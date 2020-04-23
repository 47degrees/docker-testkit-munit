package com.github.benfradet.docker.munit

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
