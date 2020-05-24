ThisBuild / scalaVersion := "2.13.2"
ThisBuild / organization := "com.47deg"

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; mdoc; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val testkit = project
  .in(file("."))
  .settings(moduleName := "docker-testkit-munit")
  .settings(testkitSettings: _*)

lazy val `documentation` = project
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
