import edu.mum.log.Log
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Issa Fikadu
  *
  */
object LogAnalyzer {

  def main(args: Array[String]) {

    val outputDir = new Path("/bigdata/analytics/output")

    val sc = new SparkContext(new SparkConf().setAppName("Apache Log Analyzer").setMaster("local"))

    val conf = new Configuration()
   /* val fs = FileSystem.get(conf)

    fs.delete(outputDir)*/
    val input = sc.textFile("/bigdata/analytics/input/access_log.txt")

    input.map(s => Log(s)).foreach(l => println(" ******************************************"+l))
    /*val totalLog = input.count()
    println("Total logs : "+totalLog)*/

    /*val output = sc.parallelize(Seq(result))
    output.saveAsTextFile("/bigdata/project/spark/output")*/
    sc.stop

  }

}
