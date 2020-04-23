package com.github.benfradet.docker.munit

import com.whisk.docker._
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.netty.NettyDockerCmdExecFactory
import com.whisk.docker.impl.dockerjava.{Docker, DockerJavaExecutorFactory}
import _root_.munit.FunSuite

class CassandraServiceSpec
    extends FunSuite
    with DockerCassandraService
    with DockerTestKit {

  override implicit val dockerFactory: DockerFactory = new DockerJavaExecutorFactory(
    new Docker(DefaultDockerClientConfig.createDefaultConfigBuilder().build(),
               factory = new NettyDockerCmdExecFactory()))

  test("cassandra node should be ready with log line checker") {
    isContainerReady(cassandraContainer).map(assert(_))
  }
}
