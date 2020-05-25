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
import _root_.munit.FunSuite

class MongodbServiceSpec
    extends FunSuite
    with DockerTestKit
    with DockerKitSpotify
    with DockerMongodbService {

  test("mongodb node should be ready with log line checker") {
    isContainerReady(mongodbContainer).map(assert(_))
    mongodbContainer.getPorts().map(m => assert(m.get(27017).nonEmpty))
    mongodbContainer.getIpAddresses().map(s => assert(s.nonEmpty))
  }
}
