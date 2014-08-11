package sapienapps.play.mail


case class AppMailService(appReply: String) extends MailService(appReply) {

  val resetText = s"We received a password reset request from this email address. If you did not reset your password, contact us at ${appReply}"
    ".\n\n\nClick below to reset your password.\n\n\n"

  val resetError = "We received a password reset request from this email address but you are not in our database. Please contact us for any further inquiries\n\n\n\n" +
    appReply

  def newUser(to: String, subject: String, content: Any) {
    mail(to, appReply, subject, content)
  }

  def resetMail(to: String, subject: String = "Password Reset", content: Any = resetText, link: String) {
    mail(to, appReply, subject, content)
  }

  def resetError(to: String, subject: String = "Password Reset", content: Any = resetError) {
    mail(to, appReply, subject, content)
  }

}
