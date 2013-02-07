package code.snippet

import net.liftweb.util.{Props, Mailer}
import net.liftweb.util.Mailer._
import net.liftweb.util.Helpers._
import net.liftweb.common.Full

class PlainTextEmailSnippet {

  def render = {

    Mailer.sendMail(
      From(Props.get("default.from", "nobody@example.org"), Full("Example Corporation")),
      Subject("Hello"),
      To(Props.get("default.to", "nobody@example.org")),
      To(Props.get("al.to", "nobody@example.org")),
      MessageHeader("X-Ignore-This", "true"),
      PlainMailBodyType("Hello from Lift") )

    "*" #> "Email queued"


  }


}
