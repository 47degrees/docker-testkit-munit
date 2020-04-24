/*
 * Copyright 2020 47 Degrees <https://47deg.com>
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
import com.spotify.docker.client.DefaultDockerClient
import com.whisk.docker.impl.spotify.SpotifyDockerFactory
import _root_.munit.FunSuite

class Neo4jServiceSpec extends FunSuite with DockerTestKit with DockerNeo4jService {

  override implicit val dockerFactory: DockerFactory = new SpotifyDockerFactory(
    DefaultDockerClient.fromEnv().build()
  )

  test("neo4j container should be ready") {
    isContainerReady(neo4jContainer).map(assert(_))
  }

}
