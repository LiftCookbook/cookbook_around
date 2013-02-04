package code.snippet

import net.liftweb.util.Mailer
import net.liftweb.util.Mailer._
import net.liftweb.util.Helpers._

class PlainTextSnippet {

  def render = {

    Mailer.sendMail(
      From("you@example.org"),
      Subject("Hello"),
      To("other@example.org"),
      To("someone@example.org"),
      MessageHeader("X-Ignore-This", "true"),
      PlainMailBodyType("Hello from Lift") )

    "*" #> "Email queued"


  }


}
