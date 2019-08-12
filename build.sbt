name := "scala-hello-word-api"

version := "0.1"

scalaVersion := "2.12.4"

organization := "com.tardelli"

libraryDependencies ++= {
  val akkaVersion = "2.5.12"
  val akkaHttp = "10.1.1"
  val akkaSwagger = "1.1.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttp,
    "com.typesafe.akka" %% "akka-http" % akkaHttp,
    "com.typesafe.play" %% "play-ws-standalone-json" % "1.1.8",
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.github.swagger-akka-http" %% "swagger-akka-http" % akkaSwagger,
    "de.heikoseeberger" %% "akka-http-play-json" % "1.17.0",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )
}