package services

import models.UserDetails
import repositories.UserDetailsRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserDetailsService @Inject()(implicit ec: ExecutionContext, userDetailsRepository: UserDetailsRepository) {

  def findUserDetails(email: String): Future[Option[UserDetails]] = {
    userDetailsRepository.findUserDetails(email)
  }

  def updateUserDetails(email: String, userDetails: UserDetails): Unit = {
    userDetailsRepository.updateUserDetails(email, userDetails)
  }
}
