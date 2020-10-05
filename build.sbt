ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "com.47deg"

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; mdoc; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

name := "docker-testkit-munit"

testFrameworks += new TestFramework("munit.Framework")
fork in Test := true

lazy val testkitV = "0.9.9"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit"                           % "0.2.0"   % Provided,
  "com.whisk"     %% "docker-testkit-core"             % testKitV  % Provided,
  "com.whisk"     %% "docker-testkit-impl-spotify"     % testKitV  % Test,
  "com.whisk"     %% "docker-testkit-impl-docker-java" % testKitV  % Test,
  "com.whisk"     %% "docker-testkit-samples"          % testKitV  % Test,
  "ch.qos.logback" % "logback-classic"                 % "1.2.3"   % Test,
  "org.postgresql" % "postgresql"                      % "42.2.16" % Test
)

lazy val `documentation` = project
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
