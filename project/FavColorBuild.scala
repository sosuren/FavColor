import sbt._
import sbt.Keys._

object FavColorBuild extends Build {

  import Settings._

  lazy val api = Project(
    id = "api",
    base = file("./FavColorAPI"),
    settings = defaultSettings ++ Seq(libraryDependencies ++= Dependencies.api)
  )

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = defaultSettings,
    aggregate = Seq(api)
  )
}

object Dependencies {

  import Versions._

  object Compile {
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % Akka
    val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % Akka
    val akkaClusterTool = "com.typesafe.akka" %% "akka-cluster-tools" % Akka
    val akkaClusterMetrics = "com.typesafe.akka" %% "akka-cluster-metrics" % Akka
  }

  import Compile._

  val api = Seq(akkaActor, akkaCluster, akkaClusterTool, akkaClusterMetrics)
}