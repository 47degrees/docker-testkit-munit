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
import com.whisk.docker.impl.dockerjava._
import com.whisk.docker.impl.spotify._
import _root_.munit.FunSuite

abstract class DockerContainerLinkingSpec extends FunSuite with DockerTestKit {

  lazy val cmdExecutor = implicitly[DockerCommandExecutor]

  val pingName  = "ping"
  val pongName  = "pong"
  val pingAlias = "pang"

  val pingService = DockerContainer("nginx:1.17.10", name = Some(pingName))

  val pongService = DockerContainer("nginx:1.17.10", name = Some(pongName))
    .withLinks(ContainerLink(pingService, pingAlias))

  override def dockerContainers = pingService :: pongService :: super.dockerContainers

  test("A DockerContainer should be linked to the specified containers upon start") {
    val ping     = cmdExecutor.inspectContainer(pingName)
    val pongPing = cmdExecutor.inspectContainer(s"$pongName/$pingAlias")

    for {
      pingState     <- ping
      pongPingState <- pongPing
    } yield {
      assert(pingState.nonEmpty)
      assertEquals(pingState, pongPingState)
    }
  }
}

class SpotifyDockerContainerLinkingSpec extends DockerContainerLinkingSpec with DockerKitSpotify
class DockerJavaDockerContainerLinkingSpec
    extends DockerContainerLinkingSpec
    with DockerKitDockerJava
