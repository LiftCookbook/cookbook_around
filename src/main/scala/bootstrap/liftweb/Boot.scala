package bootstrap.liftweb

import net.liftweb._

import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import javax.mail.internet.{MimeMessage, MimeMultipart}
import util.Props
import javax.mail.Message.RecipientType


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu

      Menu.i("Send Text Email") / "plaintext",
      Menu.i("Send HTML Email") / "htmlemail",
      Menu.i("Send Email with Attachment") / "attachment",

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"),
        "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries: _*))
    //
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery172
    JQueryModule.init()

    import net.liftweb.util.Mailer
    import javax.mail.{Authenticator, PasswordAuthentication}

    Mailer.authenticator = for {
      user <- Props.get("mail.user")
      pass <- Props.get("mail.password")
    } yield new Authenticator {
        override def getPasswordAuthentication =
          new PasswordAuthentication(user, pass)
      }


    def display(m: MimeMessage): String = {
      val nl = System.getProperty("line.separator")

      val from = "From: " + m.getFrom.map(_.toString).mkString(",")

      val to = for {
        rt <- List(RecipientType.TO, RecipientType.CC, RecipientType.BCC)
        address <- Option(m.getRecipients(rt)) getOrElse Array()
      } yield rt.toString + ": " + address.toString

      val subj = "Subject: " + m.getSubject

      def parts(mm: MimeMultipart) = (0 until mm.getCount).map(mm.getBodyPart)

      val body = m.getContent match {
        case mm: MimeMultipart =>
          val bodyParts = for (part <- parts(mm)) yield part.getContent.toString
          bodyParts.mkString(nl)

        case otherwise => otherwise.toString
      }

      List(from, to.mkString(nl), subj, body) mkString nl
    }

    // Comment out if you want to send email during dev mode:
    Mailer.devModeSend.default.set((m: MimeMessage) =>
      logger.info("Would have sent: " + display(m))
    )

  }
}
