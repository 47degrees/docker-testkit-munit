ThisBuild / scalaVersion := "2.13.10"
ThisBuild / organization := "com.47deg"

publish / skip := true

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; mdoc; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

val testKitVersion = "0.9.9"

lazy val `documentation` = project
  .dependsOn(`docker-testkit-munit`)
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(publish / skip := true)

lazy val `docker-testkit-munit` = project
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    Test / fork := true,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit"               % "[0.2.0,)"     % Provided, // scala-steward:off
      "com.whisk"     %% "docker-testkit-core" % testKitVersion % Provided,
      "com.whisk"     %% "docker-testkit-impl-spotify"     % testKitVersion % Test,
      "com.whisk"     %% "docker-testkit-impl-docker-java" % testKitVersion % Test,
      "com.whisk"     %% "docker-testkit-samples"          % testKitVersion % Test,
      "ch.qos.logback" % "logback-classic"                 % "1.4.5"        % Test,
      "org.postgresql" % "postgresql"                      % "42.5.1"       % Test
    )
  )
