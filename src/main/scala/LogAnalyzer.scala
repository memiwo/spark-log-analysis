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

    input.filter(r => !r.isEmpty).cache()

    accessByHour(input,sc)
    accessByIpAddress(input, sc)
    accessByHttpStatus(input, sc)

    sc.stop

  }

  def accessByHour(input:RDD[String], sc:SparkContext) = {
    val output = input
      .map(s => Log(s))
        .filter(log => log != null)
      .map(log => (log.dateTime.getHour, 1))
      .reduceByKey(_+_)
      .map{ case (k,v) => (v,k)}
      .sortByKey(ascending = false)
      .map{ case (v,k) => k + "," + v}

    val header = sc.parallelize(Array("hour,count"))
    val finalOutput = header.union(output)
    //Coalesce is used to group together the output from different partition in to one
    finalOutput.coalesce(1, true).saveAsTextFile("/bigdata/analytics/output/byHour")
   /* finalOutput.repartition(1).saveAsTextFile("/bigdata/analytics/output")*/
  }

  def accessByIpAddress(input:RDD[String], sc:SparkContext)  = {
    val output = input
        .map(s => Log(s))
        .filter(l => l != null)
        .map(log => (log.ipAddress, 1))
        .reduceByKey(_+_)
        .map{ case (k,v) => (v,k)}
        .sortByKey(ascending = false)
        .map{ case (v,k) => k + "," + v}

    val header = sc.parallelize(Array("ipAddress,count"))
    val finalOutput = header.union(output)
    //Coalesce is used to group together the output from different partition in to one
    finalOutput.coalesce(1, true).saveAsTextFile("/bigdata/analytics/output/byIpAddress")
    /* finalOutput.repartition(1).saveAsTextFile("/bigdata/analytics/output")*/
  }

  def accessByHttpStatus(input:RDD[String], sc:SparkContext) = {
    val output = input
      .map(s => Log(s))
      .filter(l => l != null)
      .map(log => (log.status, 1))
      .reduceByKey(_+_)
      .map{ case (k,v) => (v,k)}
      .sortByKey(ascending = false)
      .map{ case (v,k) => k + "," + v}

    val header = sc.parallelize(Array("httpStatus,count"))
    val finalOutput = header.union(output)
    //Coalesce is used to group together the output from different partition in to one
    finalOutput.coalesce(1, true).saveAsTextFile("/bigdata/analytics/output/byHttpStatus")
    /* finalOutput.repartition(1).saveAsTextFile("/bigdata/analytics/output")*/
  }


}
