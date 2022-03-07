/*
 * Copyright 2020 47 Degrees Open Source <https://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package docker.munit

import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import org.slf4j.LoggerFactory
import _root_.munit.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class DependencyGraphReadyCheckSpec extends FunSuite with DockerKitSpotify {

  override val StartContainersTimeout = 45 seconds
  override val StopContainersTimeout = 45 seconds

  private lazy val log = LoggerFactory.getLogger(this.getClass)

  val zookeeperContainer =
    DockerContainer("confluentinc/cp-zookeeper:5.5.0", name = Some("zookeeper"))
      .withHostname("zookeeper")
      .withEnv("ZOOKEEPER_TICK_TIME=2000", "ZOOKEEPER_CLIENT_PORT=2181")
      .withReadyChecker(DockerReadyChecker.LogLineContains("binding to port"))

  val kafkaContainer = DockerContainer("confluentinc/cp-kafka:5.5.0", name = Some("kafka"))
    .withHostname("broker")
    .withEnv(
      "KAFKA_BROKER_ID=1",
      "KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181",
      "KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092",
      "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT"
    )
    .withLinks(ContainerLink(zookeeperContainer, "zookeeper"))
    .withReadyChecker(DockerReadyChecker.LogLineContains("[KafkaServer id=1] started"))

  val schemaRegistryContainer =
    DockerContainer("confluentinc/cp-schema-registry:5.5.0", name = Some("schema-registry"))
      .withHostname("schema-registry")
      .withEnv(
        "SCHEMA_REGISTRY_HOST_NAME=schema-registry",
        "SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL=zookeeper:2181"
      )
      .withLinks(
        ContainerLink(zookeeperContainer, "zookeeper"),
        ContainerLink(kafkaContainer, "kafka")
      )
      .withReadyChecker(
        DockerReadyChecker.LogLineContains("Server started, listening for requests")
      )

  override def dockerContainers =
    schemaRegistryContainer :: kafkaContainer :: zookeeperContainer :: super.dockerContainers

  test("all containers except the leaves of the dep graph should be ready after initialization") {
    startAllOrFail()

    try {
      assert(containerManager.isReady(zookeeperContainer).isCompleted)
      assert(containerManager.isReady(kafkaContainer).isCompleted)
      assert(!containerManager.isReady(schemaRegistryContainer).isCompleted)

      Await.ready(containerManager.isReady(schemaRegistryContainer), 5.minutes)

      assert(containerManager.isReady(schemaRegistryContainer).isCompleted)
    } catch {
      case e: RuntimeException => log.error("Test failed during readychecks", e)
    } finally {
      Await.ready(containerManager.stopRmAll(), StopContainersTimeout)
      ()
    }
  }

  override def startAllOrFail(): Unit = {
    Await.result(containerManager.pullImages(), PullImagesTimeout)
    containerManager.initReadyAll(StartContainersTimeout).map(_.map(_._2).forall(identity))
    sys.addShutdownHook {
      containerManager.stopRmAll()
      ()
    }
    ()
  }
}
