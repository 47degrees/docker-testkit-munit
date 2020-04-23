lazy val commonSettings = Seq(
  organization := "com.github.benfradet",
  version := "0.1.0",
  scalaVersion := "2.13.1",
  fork in Test := true
)

lazy val munit = project
  .in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "docker-testkit-munit",
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies ++= Seq(
      "org.scalameta"  %% "munit"                           % "0.7.3",
      "com.whisk"      %% "docker-testkit-core"             % "0.9.9",
      "com.whisk"      %% "docker-testkit-impl-spotify"     % "0.9.9" % Test,
      "com.whisk"      %% "docker-testkit-impl-docker-java" % "0.9.9" % Test,
      "com.whisk"      %% "docker-testkit-samples"          % "0.9.9" % Test,
      "ch.qos.logback" % "logback-classic"                  % "1.2.1" % Test,
      "org.postgresql" % "postgresql"                       % "9.4.1212" % Test
    )
  )
