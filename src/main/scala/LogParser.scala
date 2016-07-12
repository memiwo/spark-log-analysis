import java.text.SimpleDateFormat
import java.time.{LocalDateTime, ZoneId}
import java.util.Date
import java.util.regex.Pattern

import edu.mum.log.Log


/**
  * @author Issa Fikadu
  *
  */
object LogParser {

  private val ipAddress = "(\\S+)"
  private val client = "(\\S+)"
  private val user = "(\\S+)"
  private val dateTime = "(\\[.+?\\])"
  private val method = "\"(\\S+)"
  private val resource = "(\\S+)"
  private val protocol = "(\\S+)\""
  private val status = "(\\d{3})"
  private val bytes = "(\\S+)"
  private val referrer = "\"(.*?)\""
  private val agent = "\"(.*?)\""
 // private val regex = s"$ip $client $user $dateTime $request $status $bytes"
  private val regex = s"$ipAddress $client $user $dateTime $method $resource $protocol $status $bytes $referrer $agent"
  private val p = Pattern.compile(regex)
  def main(args: Array[String]) {

    val str = "5.18.95.55 - - [13/Dec/2015:18:43:30 +0100] \"GET /administrator/ HTTP/1.1\" 200 4263 \"-\" \"Mozilla/5.0 (Windows NT 6.0; rv:34.0) Gecko/20100101 Firefox/34.0\" \"-\""
    val matcher = p.matcher(str)

    val outputFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss")
    val dat:Date = outputFormat.parse("22/Jan/2016:15:53:03 +0100")

    println(dat)

    val firstLog = Log(str)
    println("first log is "+firstLog)
    //new LocalDateTime()
   // println(dat.getTime)
/*    val localT = LocalDateTime.ofInstant(dat.toInstant,ZoneId.systemDefault())
    println("hour is " +localT.getHour())
    println("min is " +localT.getMinute())
    println("Day of month is " +localT.getDayOfWeek().name())
    val instant = dat.toInstant()
    val localD = LocalDate.now()
    val zdt = instant.atZone(ZoneId.systemDefault())
    val date:LocalDate = zdt.toLocalDate()*/


    if (matcher.find) {
      println(matcher.group(1))
      println(matcher.group(2))
        println(matcher.group(3))
      println(matcher.group(4).replace("[","").replace("]",""))

            println(matcher.group(5))
              println(matcher.group(6))
                println(matcher.group(7))
                println(matcher.group(8))
                println(matcher.group(9))
      println(matcher.group(10))
      println(matcher.group(11))
    }
  }
}
