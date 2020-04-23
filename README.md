## docker-testkit-munit

This is an integration for [docker-testkit](https://github.com/whisklabs/docker-it-scala/)
and [munit](https://scalameta.org/munit/).

### Usage

To add this to your SBT project:

```scala
lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += Seq(
      "com.scalameta"        %% "munit"                       % "0.7.3" % "it,test"
      "com.whisk"            %% "docker-testkit-impl-spotify" % "0.9.9" % "it",
      "com.github.benfradet" %% "docker-testkit-munit"        % "0.1.0" % "it"
    )
  )
```

Using SBT `it` settings: https://www.scala-sbt.org/1.x/docs/Testing.html#Integration+Tests.

In your integration test:

```scala
import com.github.benfradet.docker.munit.DockerTestKit
import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import munit.FunSuite

trait Nginx extends DockerTestKit with DockerKitSpotify { self: FunSuite =>
  override def dockerContainers = DockerContainer("nginx:1.7.10") :: dockerContainers
}

class IntegrationText extends FunSuite with Nginx {
  test("something related to nginx") {
    assert(...)
  }
}
```

For further documentation, refer to
[docker-testkit](https://github.com/whisklabs/docker-it-scala/).
