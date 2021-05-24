package models

case class UserDetails(
                        private val address: String,
                        private val city: String,
                        private val country: String,
                        private val postalCode: String,
                        private val phone: String,
                      ) {
  def getAddress = address

  def getCity = city

  def getCountry = country

  def getPostalCode = postalCode

  def getPhone = phone
}
