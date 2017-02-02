import sbt._
import sbt.Keys._

object Settings extends Build {

  lazy val defaultSettings = Seq(
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.8"
  )
}