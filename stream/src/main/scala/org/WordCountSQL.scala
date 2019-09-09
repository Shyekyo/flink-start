package org

/**
  * Created by zhangxiaofan on 2019/9/3.
  */
import org.apache.flink.api.scala._
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.api.scala._

/**
  * Simple example that shows how the Batch SQL API is used in Scala.
  *
  * This example shows how to:
  *  - Convert DataSets to Tables
  *  - Register a Table under a name
  *  - Run a SQL query on the registered Table
  *
  */
object WordCountSQL {

  // *************************************************************************
  //     PROGRAM
  // *************************************************************************

  def main(args: Array[String]): Unit = {

    // set up execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment
    //val tEnv = BatchTableEnvironment.create(env)
    val tEnv = TableEnvironment.getTableEnvironment(env)

    val input = env.fromElements(WC("hello", 1), WC("hello", 1), WC("ciao", 1))

    // register the DataSet as table "WordCount"
    tEnv.registerDataSet("WordCount", input, 'word, 'frequency)

    // run a SQL query on the Table and retrieve the result as a new Table
    val table = tEnv.sqlQuery("SELECT word, SUM(frequency) FROM WordCount GROUP BY word")

    table.toDataSet[WC].print()
  }

  // *************************************************************************
  //     USER DATA TYPES
  // *************************************************************************

  case class WC(word: String, frequency: Long)

}