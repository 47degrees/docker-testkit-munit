package com.github.benfradet.docker.munit

import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import _root_.munit.FunSuite

class KafkaServiceSpec
    extends FunSuite
    with DockerKafkaService
    with DockerTestKit
    with DockerKitSpotify {

  test("kafka container should be ready") {
    isContainerReady(kafkaContainer).map(assert(_))
  }

}
