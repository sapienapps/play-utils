package sapienapps.play.mail

import com.typesafe.plugin._
import play.api.Logger
import play.api.Play.current

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 9/14/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
object MailService {

  val SupportEmail = "support@marketmuse.co"

  /**
   *
   * @param subject
   * @param from
   * @param to format can be <code>"Peter Hausel Junior <noreply@email.com>","example@foo.com"</code>
   * @param text
   */
  def mail(subject: String, to: String, text: String, from: String = SupportEmail, html : Boolean = false) {
    val f = Future {
      val mail = use[MailerPlugin].email
      mail.setSubject(subject)
      mail.setRecipient(to)
      //or use a list
      mail.setFrom(from)
      //sends both text and html
      if (html) mail.sendHtml(text) else mail.send(text)
    }

    f onSuccess {
      case _ => Logger.debug("Email sent to " + to)
    }
  }

  def newUser(to: String, username: String) {
    val subject = "Welcome to MarketMuse!"
    val html =
      <html>
        <body>
          <div>
            <strong>Thanks for signing up!</strong> <br/>
            <br/>
            <span>Welcome to the MarketMuse community,
              {username}
              ! We hope that our tools help you generate more organic traffic.</span> <br/>
            <br/>
            <span>You will now have access to:</span>
            <ul>
              <li>
                <strong>Blog Analyzer:</strong>
                Generate related topics you should mention in your blog post.
                <br/>
                Add these relevant topics into your post to generate more organic traffic.
                <br/>
              </li>
              <br/>
              <li>
                <strong>Idea Generator:</strong>
                Give us 3-6 topics and we'll generate a list of related topics, scored by their potential to generate traffic for your site.
                <br/>
                Write new content on Attractive topics to increase organic traffic.
                <br/>
              </li>
              <br/>
              <li>
                <strong>Keyword Tool:</strong>
                Quickly generate related keywords. Spice up your Search Engine Marketing campaigns!
                <br/>
              </li>
            </ul>
            <br/>
            <span>Get started at</span> <a href="http://www.MarketMuse.co" target="_blank" style="word-wrap: break-word;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #202020;font-weight: bold;text-decoration: underline;">http://MarketMuse.co</a> <span>
            -- and please email me anytime with comments and feedback.</span> <br/>
            <br/>
            <span>Aki Balogh
              <br/>
              Co-Founder
              <br/>
            </span>
            <a href="mailto:aki@marketmuse.co">aki@marketmuse.co</a> <br/>
          </div>
        </body>
      </html>
    mail(subject, to, html.toString(), html = true)
  }

  def resetMail(to: String, token: String) {
    val subject = "Password Reset"
    val text =
      "We received a password reset request from this email address.  If you did not reset your password, contact us at " + SupportEmail +
        ".\n\n\nClick below to reset your password.\n\n\n" + "http://www.marketmuse.co/reset?token=" + token
    mail(subject, to, text)
  }

  def resetErrorMail(to: String) {
    val subject = "Password Reset"
    val text =
      "We received a password reset request from this email address but you are not in our database. Please contact us for any further inquiries\n\n\n\n" +
        SupportEmail
    mail(subject, to, text)
  }
}