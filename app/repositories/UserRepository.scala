package repositories

import com.mohiva.play.silhouette.api.LoginInfo
import models.{Client, User, UserBasket, UserDetails}
import play.api.Configuration
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(implicit ec: ExecutionContext, config: Configuration)
  extends AbstractMongoRepository {

  def createUser(user: User): Future[WriteResult] = {
    clientsCollection.flatMap { col =>
      col.insert.one(
        BSONDocument(
          "user" -> user,
          "userDetails" -> UserDetails("", "", "", "", ""),
          "userBasket" -> UserBasket(List(), 0.0)
        )
      )
    }
  }

  def findUser(loginInfo: LoginInfo): Future[Option[User]] = {
    clientsCollection
      .flatMap(_
        .find(BSONDocument("user.email" -> loginInfo.providerKey), Option.empty[User])
        .one[Client])
      .map(clientOption => clientOption.map(client => client.getUser))
  }

  def updateUser(user: User): Unit = {
    clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.email" -> user.getEmail),
        u = BSONDocument("$set" -> BSONDocument(
          "user" -> user
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }
}
