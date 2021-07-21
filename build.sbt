ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "com.47deg"

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; mdoc; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

name := "docker-testkit-munit"

testFrameworks += new TestFramework("munit.Framework")
fork in Test := true

val munit          = "org.scalameta" %% "munit" % "[0.2.0,)" % Provided // scala-steward:off
val testKitVersion = "0.9.9"

libraryDependencies += munit

libraryDependencies ++= Seq(
  "com.whisk"     %% "docker-testkit-core"             % testKitVersion % Provided,
  "com.whisk"     %% "docker-testkit-impl-spotify"     % testKitVersion % Test,
  "com.whisk"     %% "docker-testkit-impl-docker-java" % testKitVersion % Test,
  "com.whisk"     %% "docker-testkit-samples"          % testKitVersion % Test,
  "ch.qos.logback" % "logback-classic"                 % "1.2.4"        % Test,
  "org.postgresql" % "postgresql"                      % "42.2.23"      % Test
)

lazy val `documentation` = project
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
