package org

import org.apache.flink.api.common.accumulators.IntCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
  * Created by zhangxiaofan on 2019/9/3.
  */
object Accumulator {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements("Hello Jason What are you doing Hello world")
    val counts = text
      .flatMap(_.toLowerCase.split(" "))
      .map(new RichMapFunction[String, String] {
        val acc = new IntCounter()
        //创建累加器
        override def open(parameters: Configuration): Unit = {
          super.open(parameters)
          //注册累加器
          getRuntimeContext.addAccumulator("accumulator", acc)
        }
        override def map(in: String): String = {
          //使用累加器
          this.acc.add(1)
          in
        }
      }).map((_,1))
      .groupBy(0)
      .sum(1)
    counts.writeAsText("d:/test.txt/").setParallelism(1)
    val res = env.execute("Accumulator Test")
    //获取累加器的结果
    val num = res.getAccumulatorResult[Int]("accumulator")
    println(num)
  }
}
