package edu.mum.log

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, ZoneId}
import java.util.regex.Pattern

/**
  * @author Issa Fikadu
  *
  */
case class Log(ipAddress:String, client:String, user:String, dateTime:LocalDateTime, method:String, resource:String, protocol:String, status:Int, referrer:String, agent:String)

object Log{
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
  private val regex = s"$ipAddress $client $user $dateTime $method $resource $protocol $status $bytes $referrer $agent"
  private val p = Pattern.compile(regex)

  def apply(record: String): Log = {

    val matcher = p.matcher(record)

    if (matcher.find) {

      val outputFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss")
      val date = outputFormat.parse(matcher.group(4).replace("[","").replace("]",""))
      val localDateTime = LocalDateTime.ofInstant(date.toInstant,ZoneId.systemDefault())

      return new Log(matcher.group(1), matcher.group(2), matcher.group(3), localDateTime, matcher.group(5), matcher.group(6),
        matcher.group(7), Integer.valueOf(matcher.group(8)), matcher.group(10), matcher.group(11))
    }

    return null

  }
}