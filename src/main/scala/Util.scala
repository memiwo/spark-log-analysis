import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.IOUtils

/**
  * @author Issa Fikadu
  *
  */
object Util {

  val INPUT_FILE = new Path("/bigdata/analytics/input/access_log.txt")
  val OUTPUT_FILE = new Path("/bigdata/analytics/output")
  def prepareFileSystem(conf:Configuration):Array[String] = {

    val fs = FileSystem.get(conf)
    fs.delete(OUTPUT_FILE, true)
    if(!fs.exists(INPUT_FILE)){
      val out = fs.create(INPUT_FILE)
      val inn = getClass.getResourceAsStream("access_log.txt")
      println("******************************* resource to copy is "+inn)
      IOUtils.copyBytes(inn,out,conf)
    }




    println("===============================================")
    println("\t Using the following input/output files ")
    println("\t Input = "+INPUT_FILE)
    println("\t Output = "+OUTPUT_FILE)
    println("===============================================")
    return Array("/bigdata/analytics/input/access_log.txt", "/bigdata/analytics/output")
  }
}
