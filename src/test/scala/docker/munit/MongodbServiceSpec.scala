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
