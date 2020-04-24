## docker-testkit-munit

This is the [MUnit](https://scalameta.org/munit) integration for
[docker-testkit](https://github.com/whisklabs/docker-it-scala/).

### Usage

To leverage this in your SBT project, using SBT
[integration test settings](https://www.scala-sbt.org/1.x/docs/Testing.html#Integration+Tests):

```scala
lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += Seq(
      "com.scalameta" %% "munit"                       % "0.7.3" % "it,test"
      "com.whisk"     %% "docker-testkit-impl-spotify" % "0.9.9" % "it",
      "com.47deg"     %% "docker-testkit-munit"        % "0.1.0" % "it"
    )
  )
```

In your integration test:

```scala
import docker.munit.DockerTestKit
import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import munit.FunSuite

trait Nginx extends DockerTestKit with DockerKitSpotify { self: FunSuite =>
  override def dockerContainers = DockerContainer("nginx:1.17.10") :: dockerContainers
}

class IntegrationText extends FunSuite with Nginx {
  test("something related to nginx") {
    assert(...)
  }
}
```

For further documentation, refer to
[docker-testkit](https://github.com/whisklabs/docker-it-scala/).
