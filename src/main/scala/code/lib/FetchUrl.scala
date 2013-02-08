package code.lib

import com.ning.http.client
import dispatch._
import net.liftweb.json._

object FetchJson extends App {

  object asJson extends (client.Response => JValue) {
    def apply(r: client.Response) = JsonParser.parse(r.getResponseBody)
  }

  val svc = url("http://api.hostip.info/get_json.php?ip=62.239.237.2")

  val r : Promise[JValue] = Http(svc > asJson)

  case class HostInfo(country_name: String, country_code: String)

  implicit val formats = DefaultFormats

  println( r.map(_.extract[HostInfo])() )

}

