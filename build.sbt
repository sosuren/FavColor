import sbt._
import sbt.Keys._

lazy val projectSettings = Seq(
  organization := "com.example",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

val akkaVersion = "2.4.2"
val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion
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
  settings = projectSettings ++ Seq(name := "fav-color-api") ++ Seq(libraryDependencies ++= akkaDependencies),
  dependencies = Seq(core)
)

lazy val client = Project(
  id = "client",
  base = file("./FavColorClient"),
  settings = projectSettings ++ Seq(name := "fav-color-client") ++ Seq(libraryDependencies ++= akkaDependencies),
  dependencies = Seq(core)
)