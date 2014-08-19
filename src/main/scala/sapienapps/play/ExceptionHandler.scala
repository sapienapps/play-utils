package sapienapps.play

import java.sql.SQLException

import play.api.Logger
import play.api.mvc.Result
import play.api.mvc.Results._

/**
 * Created with IntelliJ IDEA.
 * User: jordanburke
 * Date: 11/24/13
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
class ExceptionHandler(throwable: Throwable) {

  val UNKNOWN =
    """
      Unknown Server Problem, please contact the administrator with your problem.
    """

  def handle(): Result = {
    // Walk the exception chain until the end (i.e., cause).
    val cause = getCause(throwable)
    val result = cause match {
      case e: SQLException => Conflict(JSend.fail("message" -> handleSQLError(e)))
      case ise : IllegalStateException => BadRequest(ise.getMessage)
      case se : SecurityException => Forbidden(se.getMessage)
      case _ =>
        Logger.error("Unhandled error!", cause)
        BadRequest(throwable.getMessage)
    }
    result
  }

  def handleSQLError(cause : SQLException) : String = {
    val code = cause.getSQLState
    val message = code match {
      case constraint if constraint.startsWith("23") => handleSQLUnique(cause)
      case _ =>
        Logger.error("Unhandled SQL Error!", cause)
        UNKNOWN
    }
    message
  }

  def handleSQLUnique(constraint : SQLException) : String = {
    val message = constraint.getMessage match {
      case x if x.indexOf("username") > -1 => "Username already taken, please choose another."
      case x if x.indexOf("email") > -1 => "Email already taken, please choose another."
      case _ =>
        Logger.error("Unhandled SQL Unique!", constraint)
        UNKNOWN
    }
    message
  }

  def getCause(throwable : Throwable) : Throwable = {
    var cause = throwable
    while(cause.getCause != null && cause != cause.getCause) {
      cause = cause.getCause
    }
    cause
  }
}
