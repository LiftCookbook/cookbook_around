package code.snippet

import net.liftweb.util.{Props, Mailer}
import net.liftweb.util.Mailer._
import net.liftweb.util.Helpers._

class HtmlEmailSnippet {

  def render = {

    val html = <html>
      <head>
        <title>Hello</title>
      </head>
      <body>
        <h1>Hello!</h1>
      </body>
    </html>

    var text = "Hello!"

    Mailer.sendMail(
      From(Props.get("default.from", "nobody@example.org")),
      Subject("Hello"),
      To(Props.get("default.to", "nobody@example.org")),
      PlainMailBodyType(text), // If you want a text alternative too
      XHTMLMailBodyType(html)
    )

    "*" #> "Email queued"

  }


}
