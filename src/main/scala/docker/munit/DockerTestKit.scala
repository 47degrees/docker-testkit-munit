package docker.munit

import com.whisk.docker.DockerKit

trait DockerTestKit extends DockerKit { self: munit.FunSuite =>
  override def beforeAll(): Unit =
    startAllOrFail()

  override def afterAll(): Unit =
    stopAllQuietly()
}
