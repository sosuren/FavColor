import sbt._
import sbt.Keys._

lazy val projectSettings = Seq(
  organization := "com.example",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

val akkaVersion = "2.4.2"
val sparkStreamingVer = "2.1.0"
val sparkStreamingKafkaVer = "1.6.3"
val sparkCassandraConnectorVer = "1.6.4"

val akkaDep = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion
)
val sparkStreamingDep = Seq(
  "org.apache.spark" %% "spark-streaming" % sparkStreamingVer
)
val sparkStreamingKafkaDep = Seq(
  "org.apache.spark" %% "spark-streaming-kafka" % sparkStreamingKafkaVer
)
val apiDep = akkaDep ++ Seq(
  "org.apache.spark" %% "spark-streaming" % sparkStreamingVer,
  "com.datastax.spark" %% "spark-cassandra-connector" % sparkCassandraConnectorVer,
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % sparkCassandraConnectorVer
)

lazy val root = Project(
  id = "root",
  base = file("."),
  settings = projectSettings ++ Seq(name := "fav-color"),
  aggregate = Seq(core, api)
)

lazy val core = Project(
  id = "core",
  base = file("./FavColorCore"),
  settings = projectSettings ++ Seq(name := "fav-color-core")
)

lazy val api = Project(
  id = "api",
  base = file("./FavColorAPI"),
  settings = projectSettings ++ Seq(name := "fav-color-api") ++ Seq(libraryDependencies ++= apiDep),
  dependencies = Seq(core)
)

lazy val client = Project(
  id = "client",
  base = file("./FavColorClient"),
  settings = projectSettings ++ Seq(name := "fav-color-client") ++ Seq(libraryDependencies ++= akkaDep),
  dependencies = Seq(core)
)