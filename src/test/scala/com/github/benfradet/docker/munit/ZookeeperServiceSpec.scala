package com.github.benfradet.docker.munit

import com.whisk.docker._
import com.whisk.docker.impl.dockerjava.DockerKitDockerJava
import _root_.munit.FunSuite

class ZookeeperServiceSpec
    extends FunSuite
    with DockerZookeeperService
    with DockerTestKit
    with DockerKitDockerJava {

  test("zookeeper container should be ready") {
    isContainerReady(zookeeperContainer).map(assert(_))
  }

}
