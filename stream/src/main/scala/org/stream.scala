package org

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by zhangxiaofan on 2019/9/2.
  */
object stream {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
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
    datastream.
      //flatMap(_.split(",")).
      map((_,1)).
      keyBy(0).
      timeWindow(Time.seconds(5),Time.seconds(3)).
      reduce((a,b) => (a._1,a._2+b._2)).print()
    env.execute("example")
  }
}
