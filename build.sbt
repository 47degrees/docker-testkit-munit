addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; test")
addCommandAlias("ci-docs", "github; project-docs/mdoc; headerCreateAll")

lazy val testkit = project
  .in(file("."))
  .settings(moduleName := "docker-testkit-munit")
  .settings(testkitSettings: _*)

lazy val `project-docs` = project
  .in(file(".docs"))
  .aggregate(testkit)
  .settings(moduleName := "docker-testkit-munit-project-docs")
  .settings(mdocIn := file(".docs"))
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
  .enablePlugins(MdocPlugin)
