package code.snippet

import net.liftweb.util.{Props, Mailer}
import net.liftweb.util.Mailer._
import net.liftweb.util.Helpers._
import net.liftweb.common.{Box, Full}
import net.liftweb.http.LiftRules

class EmailWithAttachmentSnippet {

  def render = {

    val content =
      "Planet,Discoverer\r\n" +
      "HR 8799 c, Marois et al\r\n" +
      "Kepler-22b, Kepler Science Team\r\n"

    case class CSVFile(
      bytes: Array[Byte],
      filename: String = "file.csv",
      mime: String = "text/csv; charset=utf8; header=present" )

    val attach = CSVFile(content.mkString.getBytes("utf8"))

    val body = <p>Please research the enclosed.</p>

    val msg = XHTMLPlusImages(body,
      PlusImageHolder(attach.filename, attach.mime, attach.bytes))


    Mailer.sendMail(
      From(Props.get("default.from", "nobody@example.org"), Full("Example Corporation")),
      Subject("Planets"),
      To(Props.get("default.to", "nobody@example.org")),
      msg)

    "*" #> "Email queued"

  }


  def sendAsAttachment = planetImage(true)

  def sendAsInline = planetImage(false)

  private def planetImage(attach_? : Boolean) = {

    val filename = "Kepler-22b_System_Diagram.jpg"

    val msg =
      for ( bytes <- LiftRules.loadResource("/"+filename) )
      yield XHTMLPlusImages(
        <p>Please research this planet.</p>,
        PlusImageHolder(filename, "image/jpg", bytes, attach_?))

    msg match {

      case Full(m) =>
        Mailer.sendMail(
          From(Props.get("default.from", "nobody@example.org"), Full("Example Corporation")),
          Subject("Planet attachment="+attach_?),
          To(Props.get("default.to", "nobody@example.org")),
          m)

        "*" #> "Email queued"

      case _ =>
        "*" #> "Image not found"

    }

  }

}
