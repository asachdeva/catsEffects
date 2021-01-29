import sbt._

object Dependencies {

  object Versions {
    val circe        = "0.13.0"
    val doobie       = "0.10.0"
    val fs2          = "2.5.0"
    val fs2Kafka     = "1.3.1"
    val http4s       = "0.21.16"
    val kafkaClients = "2.7.0"
    val log4cats     = "1.1.1"
    val nscalaTime   = "2.26.0"
    val opentracing  = "3.0.0"
    val pureconfig   = "0.14.0"
    val scalaLogging = "3.9.2"
    val sttp         = "2.2.9"
    val tapir        = "0.17.7"

    // Test
    val embeddedKafka    = "2.7.0"
    val embeddedPostgres = "1.2.10"
    val flyway           = "7.5.2"
    /*
      Using embedded postgres binaries 11.10.0-1 instead of 11.8.0 because binaries 11.8.0 are missing libz libraries
        Issue: https://github.com/zonkyio/embedded-postgres-binaries/issues/21
        Resolution: https://github.com/zonkyio/embedded-postgres-binaries/issues/21#issuecomment-752792000
      NOTE: Remove this comment once we upgrade to next version of postgres binaries since it will will not be needed anymore
     */
    val postgresBinaries = "11.10.0-1"
    val scalaCheck       = "1.15.2"
    val scalaMock        = "5.1.0"
    val scalaTest        = "3.2.3"
    val scalaTestCheck   = "3.2.2.0"

    // Runtime
    val janino         = "3.1.3"
    val logback        = "1.2.3"
    val logbackEncoder = "6.6"
    val sentry         = "3.2.1"
  }

  def circe(artifact: String, version: String): ModuleID       = "io.circe"                     %% artifact % version
  def doobie(artifact: String, version: String): ModuleID      = "org.tpolecat"                 %% artifact % version
  def fs2(artifact: String, version: String): ModuleID         = "co.fs2"                       %% artifact % version
  def http4s(artifact: String, version: String): ModuleID      = "org.http4s"                   %% artifact % version
  def opentracing(artifact: String, version: String): ModuleID = "com.colisweb"                 %% artifact % version
  def sttp(artifact: String, version: String): ModuleID        = "com.softwaremill.sttp.client" %% artifact % version
  def tapir(artifact: String, version: String): ModuleID       = "com.softwaremill.sttp.tapir"  %% artifact % version

  def postgres(artifact: String, version: String): ModuleID = {
    val osVersion = System.getProperty("os.name").toLowerCase match {
      case osName if osName.contains("mac") =>
        "embedded-postgres-binaries-darwin-amd64"
      case osName if osName.contains("win") =>
        "embedded-postgres-binaries-windows-amd64"
      case osName if osName.contains("linux") =>
        "embedded-postgres-binaries-linux-amd64"
      case osName => throw new RuntimeException(s"Unknown operating system $osName")
    }
    artifact % osVersion % version
  }

  lazy val circeCore            = circe("circe-core", Versions.circe)
  lazy val circeFs2             = circe("circe-parser", Versions.circe)
  lazy val circeGeneric         = circe("circe-generic", Versions.circe)
  lazy val circeParser          = circe("circe-fs2", Versions.circe)
  lazy val doobieCore           = doobie("doobie-core", Versions.doobie)
  lazy val doobieHikari         = doobie("doobie-hikari", Versions.doobie)
  lazy val doobiePostgres       = doobie("doobie-postgres", Versions.doobie)
  lazy val flyway               = "org.flywaydb"                % "flyway-core"   % Versions.flyway
  lazy val fs2Core              = fs2("fs2-core", Versions.fs2)
  lazy val fs2IO                = fs2("fs2-io", Versions.fs2)
  lazy val fs2Kafka             = "com.github.fd4s"            %% "fs2-kafka"     % Versions.fs2Kafka
  lazy val http4sCirce          = http4s("http4s-circe", Versions.http4s)
  lazy val http4sDSL            = http4s("http4s-dsl", Versions.http4s)
  lazy val http4sServer         = http4s("http4s-blaze-server", Versions.http4s)
  lazy val kafkaClients         = "org.apache.kafka"            % "kafka-clients" % Versions.kafkaClients
  lazy val log4cats             = "io.chrisdavenport"          %% "log4cats-core" % Versions.log4cats
  lazy val nscalaTime           = "com.github.nscala-time"     %% "nscala-time"   % Versions.nscalaTime
  lazy val opentracingAmqp      = opentracing("scala-opentracing-amqp", Versions.opentracing)
  lazy val opentracingClient    = opentracing("scala-opentracing-http4s-client-blaze", Versions.opentracing)
  lazy val opentracingContext   = opentracing("scala-opentracing-context", Versions.opentracing)
  lazy val opentracingCore      = opentracing("scala-opentracing-core", Versions.opentracing)
  lazy val opentracingServer    = opentracing("scala-opentracing-http4s-server-tapir", Versions.opentracing)
  lazy val pureconfig           = "com.github.pureconfig"      %% "pureconfig"    % Versions.pureconfig
  lazy val scalaLogging         = "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging
  lazy val sttpCats             = sttp("cats", Versions.sttp)
  lazy val sttpCatsBackend      = sttp("async-http-client-backend-cats", Versions.sttp)
  lazy val sttpCirce            = sttp("circe", Versions.sttp)
  lazy val sttpCore             = sttp("core", Versions.sttp)
  lazy val sttpFs2Backend       = sttp("async-http-client-backend-fs2", Versions.sttp)
  lazy val tapirCirce           = tapir("tapir-json-circe", Versions.tapir)
  lazy val tapirCore            = tapir("tapir-core", Versions.tapir)
  lazy val tapirHttp4sServer    = tapir("tapir-http4s-server", Versions.tapir)
  lazy val tapirOpenApiDocs     = tapir("tapir-openapi-docs", Versions.tapir)
  lazy val tapirOpenApiYaml     = tapir("tapir-openapi-circe-yaml", Versions.tapir)
  lazy val tapirRedocHttp4s     = tapir("tapir-redoc-http4s", Versions.tapir)
  lazy val tapirSwaggerUiHttp4s = tapir("tapir-swagger-ui-http4s", Versions.tapir)

  // Runtime
  lazy val janino         = "org.codehaus.janino"  % "janino"                   % Versions.janino
  lazy val logback        = "ch.qos.logback"       % "logback-classic"          % Versions.logback
  lazy val logbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % Versions.logbackEncoder

  lazy val scalaPbRunTime =
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf" // The version comes from the scalapb plugin.
  lazy val sentry = "io.sentry" % "sentry" % Versions.sentry

  // Test
  lazy val doobieTest       = doobie("doobie-scalatest", Versions.doobie)
  lazy val embeddedKafka    = "io.github.embeddedkafka" %% "embedded-kafka"    % Versions.embeddedKafka
  lazy val embeddedPostgres = "io.zonky.test"            % "embedded-postgres" % Versions.embeddedPostgres
  lazy val postgresBinaries = postgres("io.zonky.test.postgres", Versions.postgresBinaries)
  lazy val scalaCheck       = "org.scalacheck"          %% "scalacheck"        % Versions.scalaCheck
  lazy val scalaMock        = "org.scalamock"           %% "scalamock"         % Versions.scalaMock
  lazy val scalaTest        = "org.scalatest"           %% "scalatest"         % Versions.scalaTest
  lazy val scalaTestCheck   = "org.scalatestplus"       %% "scalacheck-1-14"   % Versions.scalaTestCheck

  val circeDeps  = Seq(circeCore, circeFs2, circeGeneric, circeParser)
  val doobieDeps = Seq(doobieCore, doobieHikari, doobiePostgres)
  val fs2Deps    = Seq(fs2Core, fs2IO, fs2Kafka)
  val http4sDeps = Seq(http4sCirce, http4sDSL, http4sServer)

  val opentracingDeps =
    Seq(opentracingAmqp, opentracingClient, opentracingContext, opentracingCore, opentracingServer)
  val scalaPbDeps = Seq(scalaPbRunTime)
  val sttpDeps    = Seq(sttpCats, sttpCatsBackend, sttpCirce, sttpCore, sttpFs2Backend)

  val tapirDeps = Seq(
    tapirCirce,
    tapirCore,
    tapirHttp4sServer,
    tapirOpenApiDocs,
    tapirOpenApiYaml,
    tapirRedocHttp4s,
    tapirSwaggerUiHttp4s
  )

  val fluxusTestDeps =
    Seq(
      doobieTest,
      embeddedKafka,
      embeddedPostgres,
      postgresBinaries,
      scalaCheck,
      scalaMock,
      scalaTest,
      scalaTestCheck
    ).map(_ % Test)
}
