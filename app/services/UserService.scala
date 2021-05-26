package services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User
import repositories.UserRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(implicit ec: ExecutionContext, userRepository: UserRepository)
  extends IdentityService[User] {

  def createUser(user: User): Unit = {
    userRepository.createUser(user)
  }

  def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
    userRepository.findUser(loginInfo)
  }

  def updateUser(user: User): Unit = {
    userRepository.updateUser(user)
  }
}
