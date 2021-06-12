package models

import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import com.mohiva.play.silhouette.password.BCryptSha256PasswordHasher
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class User(
                 private val email: String,
                 private val password: Option[String] = None,
                 private val name: String,
               ) extends Identity {
  def getEmail = email

  def getPassword = password

  def getName = name

  /**
   * Generates login info from email
   *
   * @return login info
   */
  def loginInfo = LoginInfo(CredentialsProvider.ID, email)

  /**
   * Generates password info from password.
   *
   * @return password info
   */
  def passwordInfo = PasswordInfo(BCryptSha256PasswordHasher.ID, password.get)
}

object User {
  implicit def userWriter: BSONDocumentWriter[User] = Macros.writer[User]

  implicit def userReader: BSONDocumentReader[User] = Macros.reader[User]
}
