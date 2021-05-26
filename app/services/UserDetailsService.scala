package services

import models.UserDetails
import repositories.UserDetailsRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserDetailsService @Inject()(implicit ec: ExecutionContext, userDetailsRepository: UserDetailsRepository) {

  def findUserDetails(username: String): Future[Option[UserDetails]] = {
    userDetailsRepository.findUserDetails(username)
  }

  def updateUserDetails(username: String, userDetails: UserDetails): Unit = {
    userDetailsRepository.updateUserDetails(username, userDetails)
  }
}
