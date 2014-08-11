package sapienapps.play.mail

import com.typesafe.plugin._
import play.api.Logger
import play.api.Play.current

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.xml.Elem

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 9/14/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
class MailService(val replyEmail : String) {

  /**
   * @param from email address from
   * @param to format can be <code>"Peter Hausel Junior <noreply@email.com>","example@foo.com"</code>
   * @param subject mail subject
   * @param content can be either plain text or html (i.e. Elem)
   */
  def mail(to: String, from: String = replyEmail, subject: String, content : Any) {
    val isHTML = content.isInstanceOf[Elem]
    val f = Future {
      val mail = use[MailerPlugin].email
      mail.setSubject(subject)
      mail.setRecipient(to)
      //or use a list
      mail.setFrom(from)
      //sends both text and html
      if (isHTML) mail.sendHtml(content.toString) else mail.send(content.toString)
    }

    f onSuccess {
      case _ => Logger.debug("Email sent to " + to)
    }
  }


}