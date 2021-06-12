package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class UserDetails(
                        private val name: String,
                        private val surname: String,
                        private val address: String,
                        private val city: String,
                        private val country: String,
                        private val postalCode: String,
                        private val phone: String,
                      ) {
  def getName = name

  def getSurname = surname

  def getAddress = address

  def getCity = city

  def getCountry = country

  def getPostalCode = postalCode

  def getPhone = phone
}

object UserDetails {
  implicit def userDetailsWriter: BSONDocumentWriter[UserDetails] = Macros.writer[UserDetails]

  implicit def userDetailsReader: BSONDocumentReader[UserDetails] = Macros.reader[UserDetails]
}