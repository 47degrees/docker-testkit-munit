ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "com.47deg"

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; mdoc; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

name := "docker-testkit-munit"

testFrameworks += new TestFramework("munit.Framework")
fork in Test := true

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit"                           % "0.7.7",
  "com.whisk"     %% "docker-testkit-core"             % "0.9.9",
  "com.whisk"     %% "docker-testkit-impl-spotify"     % "0.9.9"   % Test,
  "com.whisk"     %% "docker-testkit-impl-docker-java" % "0.9.9"   % Test,
  "com.whisk"     %% "docker-testkit-samples"          % "0.9.9"   % Test,
  "ch.qos.logback" % "logback-classic"                 % "1.2.3"   % Test,
  "org.postgresql" % "postgresql"                      % "42.2.12" % Test
)

lazy val `documentation` = project
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
