import edu.mum.log.Log
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Issa Fikadu
  *
  */
object LogAnalyzer {

  def main(args: Array[String]) {


    val sc = new SparkContext(new SparkConf().setAppName("Apache Log Analyzer").setMaster("local"))

    val conf = new Configuration()
    val fs = FileSystem.get(conf)

    val paths = Util.prepareFileSystem(conf)

    val outputDir = new Path(paths(1))

    fs.delete(outputDir)

    val input = sc.textFile(paths(0))

    input.cache()

    accessByHour(input,sc)

    sc.stop

  }

  def accessByHour(input:RDD[String], sc:SparkContext) = {
    val output = input.
      filter(r => !r.isEmpty)
      .map(s => Log(s))
      .map(log => (log.dateTime.getHour, 1))
      .reduceByKey(_+_)
      .map{ case (k,v) => (v,k)}
      .sortByKey(ascending = false)
      .map{ case (v,k) => k + "," + v}

    val header = sc.parallelize(Array("hour,count"))
    val finalOutput = header.union(output)
    //Coalesce is used to group together the output from different partition in to one
    finalOutput.coalesce(1, true).saveAsTextFile("/bigdata/analytics/output")
   /* finalOutput.repartition(1).saveAsTextFile("/bigdata/analytics/output")*/
  }

  def accessByIpAddress(input:RDD[String], sc:SparkContext)  = {

  }


}
