package sapienapps.play

import java.util.Date

import play.api.mvc.RequestHeader

/**
 * Created by Jordan Burke on 6/29/2014.
 */
object ELF {

  /**
  #Software: Microsoft Internet Information Services 6.0
  #Version: 1.0
  #Date: 2002-05-02 17:42:15
  #Fields: date time c-ip cs-username s-ip s-port cs-method cs-uri-stem cs-uri-query sc-status cs(User-Agent)
  2002-05-02 17:42:15 172.22.255.255 - 172.30.255.255 80 GET /images/picture.jpg - 200 Mozilla/4.0+(compatible;MSIE+5.5;+Windows+2000+Server)
    */
  def toLog(request: RequestHeader, simple: Boolean = false): String = {
    val sb = StringBuilder.newBuilder
    val date = new Date()
    append(sb, date, simple)
    append(sb, request.remoteAddress, simple)
    // TODO Server IP
    // TODO Server Port
    append(sb, request.headers.get("Cookie").getOrElse("(No Cookies)"), simple)
    append(sb, request.method)
    append(sb, request.path)
    // TODO Status
    append(sb, request.headers.get("User-Agent").getOrElse("(No Agent)"), simple)
    sb.toString()
  }

  private def append(sb: StringBuilder, any: Any, simple: Boolean = false) {
    if (!simple) {
      if (sb.nonEmpty) {
        sb.append(" ")
      }
      sb.append(any)
    }
  }
}
