package sapienapps.security

import java.security.MessageDigest

import org.mindrot.jbcrypt.BCrypt

/**
 * Created by Jordan on 6/13/2014.
 */
object Encrypt {
  val MD5 = MessageDigest.getInstance("MD5")

  /**
   * Creates a password encrypted string using BCrypt
   * @param clearString - basic string to be encrypted
   * @param rounds - number of encryption rounds (Defaults to 10)
   * @return String BCrypt
   */
  def createPassword(clearString: String, rounds : Int = 10): String = {
    if (clearString == null) {
      throw new Exception("No password defined!")
    }
    val salt = BCrypt.gensalt(rounds)
    println(salt)
    BCrypt.hashpw(clearString, salt)
  }

  /**
   * @param candidate the clear text
   * @param encryptedPassword the encrypted password string to check.
   * @return true if the candidate matches, false otherwise.
   */
  def checkPassword(candidate: String, encryptedPassword: String): Boolean = {
    if (candidate == null || encryptedPassword == null) {
      return false
    }
    BCrypt.checkpw(candidate, encryptedPassword)
  }

  /**
   * Creates a simple hash using only 4 rounds. Mostly a convenience and clarity
   * function since this can also be done via createPassword.
   * @param string - String to be hashed.
   * @return String of BCrypt hash
   */
  def createHash(string : String, rounds : Int = 4): String = {
    createPassword(string, rounds)
  }

  /**
   * Checks a hash. Mostly a convenience and clarity
   * function since this can also be done via checkHash.
   * @param string - string to validate
   * @param hash - hash String to validate against
   * @return true if the candidate matches, false otherwise.
   */
  def checkHash(string : String, hash : String): Boolean = {
    checkPassword(string, hash)
  }

  /**
   * Generate a random token.
   * @return a URL safe token
   */
  def generateToken: String = {
    //split the hash based on $ signs
    val hash = createPassword(BCrypt.gensalt(4)).split('$')

    //first get the digest part of the password
    val str = hash(3).substring(22)

    //make it url safe
    str.replace("+","-").replace("/","_").replace("=", ".")
  }

  /**
   * Generates a MD5 hash from a string object.
   * @param string - to generate MD5 hash from
   * @return String MD5
   */
  def md5(string : String) : String = {
    val sb = new StringBuilder
    val bytes = MD5.digest(string.getBytes)
    // Convert the bytes into a readable string:
    for (i <- 0 until bytes.length) {
      sb.append(Integer.toString((bytes(i) & 0xff) + 0x100, 16).substring(1))
    }
    sb.toString()
  }

  /**
   * Checks to see if a String matches a MD5 String.
   * @param string - String to check
   * @param hash - MD5 hash to validate against
   * @return Boolean whether or not string matches MD5 hash
   */
  def checkMD5Hash(string : String, hash : String) : Boolean = {
    val newHash = md5(string)
    newHash == hash
  }

  def main(args: Array[String]) {
    println("Running Encrypt Example...")
    val password = createPassword("password")
    println("Starting bcrypt test.")
    val start = System.currentTimeMillis()
    for (i <- 0 to 100) {
      checkPassword("password", password)
    }
    println("Took " + (System.currentTimeMillis() - start))

    val UUID1 = java.util.UUID.randomUUID().toString
    val UUID2 = java.util.UUID.randomUUID().toString
    val hash = md5(UUID1 + UUID2)
    println("Starting hash test.")
    val hashStart = System.currentTimeMillis()
    for (i <- 0 to 100) {
      checkMD5Hash(UUID1 + UUID2, hash)
    }
    println("Took " + (System.currentTimeMillis() - hashStart))
  }
}
