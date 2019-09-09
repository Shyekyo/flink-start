package org


import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by zhangxiaofan on 2019/9/2.
  */
object stream {
  var a = Nil
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //获取检查点配置
    val config = env.getCheckpointConfig
    //启用外部的存储的检查点
    config.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    //设置检查点，exactly_once语义和at least once 语义
    env.enableCheckpointing(5000,CheckpointingMode.EXACTLY_ONCE)
    //设置缓冲区，缓冲时间达到时，触发操作
    env.setBufferTimeout(100)
    //设置触发的时间，包括 eventtime 真实记录的时间，IngestionTime，ProcessingTime系统时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    /*val dataStream = env.generateSequence(1,10)
    val ds = dataStream.map(index  => index+1)
    ds.print()*/
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)
    // make parameters available in the web interface
    //env.getConfig.setGlobalJobParameters(params)

    // get input data
    val datastream =
    // read the text file from given input path
    if (params.has("input")) {
      env.readTextFile(params.get("input"))
    } else {
      println("Executing with default inputs data set.")
      println("Use --input to specify stream input.")
      // get default test text data
      //env.readTextFile("D:\\GITRepo\\flink-start\\stream\\src\\main\\resources\\example.csv")
      env.socketTextStream("localhost",5000)
    }
    val d = datastream.
      flatMap(_.split(",")).
      map((_,1)).
      keyBy(0).//rebalance
      timeWindow(Time.seconds(5)).
      reduce((a,b) => (a._1,a._2+b._2)).setParallelism(1)
    d.print()
    env.execute("example")
  }
}

