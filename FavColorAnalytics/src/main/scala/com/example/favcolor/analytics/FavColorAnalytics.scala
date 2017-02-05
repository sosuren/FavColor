package com.example.favcolor.analytics

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

case class AggregateLog(id: String, ts: Long, sum: Long)
case class AggregateResult(id: String, avg: Double)

object FavColorAnalytics extends App {

  val conf = new SparkConf().setMaster("local[2]").setAppName("FavColorAnalytics")
  val ssc = new StreamingContext(conf, Seconds(1))

  val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181", "fav-color-analytics-consumer", Map[String, Int]("fav-color-analytics" -> 1), StorageLevel.DISK_ONLY_2)

  kafkaStream.map(_._2).map{ pollIdWithTime =>
    (pollIdWithTime.split(":")(0), 1)
  } reduceByKeyAndWindow(reduceFunc, Duration(3000)) print()

  def reduceFunc(i1: Int, i2: Int) = i1 + i2

  ssc.start()
  ssc.awaitTermination()
}