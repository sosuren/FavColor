package com.example.favcolor.client

import akka.pattern.ask
import akka.actor.{ActorPath, ActorSystem}
import akka.cluster.client.{ClusterClientSettings, ClusterClient}
import akka.util.Timeout
import com.example.favcolor.core.PollMessages._
import com.typesafe.config.ConfigFactory

object FavColorClient extends App {

  val port = if (args.isEmpty) 0 else args(0)

  val system = ActorSystem(
    "fav-color-client",
    ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
      .withFallback(ConfigFactory.load())
  )

  val initialContactPoints = Set(
    ActorPath.fromString("akka.tcp://fav-color-api@127.0.0.1:2551/system/receptionist"),
    ActorPath.fromString("akka.tcp://fav-color-api@127.0.0.1:2552/system/receptionist")
  )

  val colorPoll = system.actorOf(ClusterClient.props(
    ClusterClientSettings(system).withInitialContacts(initialContactPoints)
  ))

  println("Sleeping ....")

  Thread.sleep(6000)

  println("Sleeping Done!!!")

  colorPoll ! ClusterClient.Send("/user/colorPoll", Vote(ColorSelect.First), localAffinity =  false)
  colorPoll ! ClusterClient.Send("/user/colorPoll", Vote(ColorSelect.Second), localAffinity = false)

  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout: Timeout = 2 seconds

  (colorPoll ? ClusterClient.Send("/user/colorPoll", GetPoll, localAffinity =  false)).mapTo[Poll] map {
    case poll =>
      println(s"Got Poll: $poll")

      (colorPoll ? ClusterClient.Send("/user/colorPoll", NextColorPoll, localAffinity = false)).mapTo[Poll] map {
        case nextPoll =>
          println(s"Next Poll: $nextPoll")
      }
  }
}