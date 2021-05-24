package models

case class User(
                 private val username: String,
                 private val password: String,
                 private val email: String,
               ) {
  def getName = username

  def getPassword = password

  def getEmail = email
}
