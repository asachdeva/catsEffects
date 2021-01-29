import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

// Reload Sbt on changes to sbt or dependencies
Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / startYear := Some(2021)
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "net.asachdeva"
ThisBuild / organizationName := "akshay"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.4"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

val format = taskKey[Unit]("Format files using scalafmt and scalafix")

promptTheme := PromptTheme(
  List(
    text(_ => "[asachdeva]", fg(64)).padRight(" λ ")
  )
)

lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion := "2.13.4",
  scalacOptions ++= Seq(
    "-Wconf:cat=unused-imports:info",
    "-Wconf:cat=unused-locals:info",
    "-Wconf:cat=unused-params:info",
    "-Ywarn-macros:after"
  )
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  skip.in(publish) := true,
  fork := true
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

lazy val `catsEffect` = project
  .in(file("."))
  .enablePlugins()
  .configs(IntegrationTest)
  .settings(
    baseSettings,
    Defaults.itSettings,
    testSettings,
    libraryDependencies ++= circeDeps ++ doobieDeps ++ fs2Deps ++ http4sDeps ++ sttpDeps ++ tapirDeps,
    libraryDependencies ++= Seq(
      janino,
      kafkaClients,
      log4cats,
      logback,
      logbackEncoder,
      pureconfig,
      scalaLogging
    ),
    libraryDependencies ++= fluxusTestDeps
  )

addCommandAlias("cover", ";clean ;coverageOn ;test ;coverageReport ;coverageOff")
addCommandAlias("format", ";scalafmtAll ;scalafmtSbt ;scalafixAll")
addCommandAlias("formatCheck", ";scalafmtCheck ;scalafmtSbtCheck ;scalafix --check ;test:scalafix --check")

// CI build
addCommandAlias("build", ";clean;+test;+assembly")
