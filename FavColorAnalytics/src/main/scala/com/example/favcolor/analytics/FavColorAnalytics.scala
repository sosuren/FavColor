package com.example.favcolor.analytics

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FavColorAnalytics extends App {

  val conf = new SparkConf().setMaster("local[2]").setAppName("FavColorAnalytics")
  val ssc = new StreamingContext(conf, Seconds(1))

  val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181", "fav-color-analytics-consumer", Map[String, Int]("fav-color-analytics" -> 1), StorageLevel.DISK_ONLY_2)

  kafkaStream.print()

  ssc.start()
  ssc.awaitTermination()
}