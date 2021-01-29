resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository".at("https://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("ch.epfl.scala"             % "sbt-bloop"           % "1.4.6")
addSbtPlugin("ch.epfl.scala"             % "sbt-scalafix"        % "0.9.25")
addSbtPlugin("com.eed3si9n"              % "sbt-buildinfo"       % "0.10.0")
addSbtPlugin("com.jsuereth"              % "sbt-pgp"             % "2.1.1")
addSbtPlugin("com.scalapenos"            % "sbt-prompt"          % "1.0.2")
addSbtPlugin("com.typesafe.sbt"          % "sbt-git"             % "1.0.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-native-packager" % "1.8.0")
addSbtPlugin("com.thesamet"              % "sbt-protoc"          % "0.99.34") // required for ScalaPB
addSbtPlugin("de.gccc.sbt"               % "sbt-jib"             % "0.8.0")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"        % "0.1.16")
addSbtPlugin("org.scalameta"             % "sbt-mdoc"            % "2.2.16")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"        % "2.4.2")
addSbtPlugin("org.scoverage"             % "sbt-scoverage"       % "1.6.1")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.8" // required for ScalaPB
