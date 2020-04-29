import sbt.Keys._
import sbt._
import com.alejandrohdezma.sbt.github.SbtGithubPlugin

object ProjectPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements

  override def requires: Plugins = SbtGithubPlugin

  object autoImport {

    lazy val testkitSettings = Seq(
      libraryDependencies ++= Seq(
        "org.scalameta"  %% "munit"                           % "0.7.4",
        "com.whisk"      %% "docker-testkit-core"             % "0.9.9",
        "com.whisk"      %% "docker-testkit-impl-spotify"     % "0.9.9" % Test,
        "com.whisk"      %% "docker-testkit-impl-docker-java" % "0.9.9" % Test,
        "com.whisk"      %% "docker-testkit-samples"          % "0.9.9" % Test,
        "ch.qos.logback" % "logback-classic"                  % "1.2.3" % Test,
        "org.postgresql" % "postgresql"                       % "9.4.1212" % Test
      )
    )
  }

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      organization := "com.47deg",
      organizationName := "47 Degrees",
      organizationHomepage := Some(url("https://47deg.com")),
      scalaVersion := "2.13.2",
      testFrameworks += new TestFramework("munit.Framework"),
      fork in Test := true
    )
}
