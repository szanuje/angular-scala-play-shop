package services

import models._
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}
import repository.MongoRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ClientService @Inject()(implicit ec: ExecutionContext, mongoRepository: MongoRepository) {

  implicit def clientWriter: BSONDocumentWriter[Client] = Macros.writer[Client]

  implicit def clientReader: BSONDocumentReader[Client] = Macros.reader[Client]

  implicit def userWriter: BSONDocumentWriter[User] = Macros.writer[User]

  implicit def userReader: BSONDocumentReader[User] = Macros.reader[User]

  implicit def userDetailsWriter: BSONDocumentWriter[UserDetails] = Macros.writer[UserDetails]

  implicit def userDetailsReader: BSONDocumentReader[UserDetails] = Macros.reader[UserDetails]

  implicit def userBasketWriter: BSONDocumentWriter[UserBasket] = Macros.writer[UserBasket]

  implicit def userBasketReader: BSONDocumentReader[UserBasket] = Macros.reader[UserBasket]

  implicit def basketProductWriter: BSONDocumentWriter[BasketProduct] = Macros.writer[BasketProduct]

  implicit def basketProductReader: BSONDocumentReader[BasketProduct] = Macros.reader[BasketProduct]

  implicit def productWriter: BSONDocumentWriter[Product] = Macros.writer[Product]

  implicit def productReader: BSONDocumentReader[Product] = Macros.reader[Product]

  implicit val personHandler = Macros.handler[UserDetails]


  def createClient(client: Client): Unit = {
    mongoRepository.clientsCollection.flatMap { col =>
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.username" -> client.getUser.getName),
        u = BSONDocument("$set" -> BSONDocument(
          "user" -> client.getUser,
          "userDetails" -> client.getUserDetails,
          "userBasket" -> client.getUserBasket
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    }
  }

  def findAllClients(): Future[List[Client]] = {
    mongoRepository.clientsCollection
      .flatMap(_
        .find(BSONDocument())
        .cursor[Client]()
        .collect(err = Cursor.FailOnError[List[Client]]())
      )
  }

  def findClient(username: String): Future[Option[Client]] = {
    mongoRepository.clientsCollection
      .flatMap(_
        .find(BSONDocument("user.username" -> username))
        .one[Client]
      )
  }

  def createUser(user: User): Unit = {
    createClient(
      Client(user, UserDetails("", "", "", "", ""), UserBasket(List(), 0.0))
    )
  }

  def findUser(username: String): Future[Option[User]] = {
    mongoRepository.clientsCollection
      .flatMap(_
        .find(BSONDocument("user.username" -> username))
        .one[Client])
      .map(clientOption => clientOption.map(client => client.getUser))
  }

  def findUserDetails(username: String): Future[Option[UserDetails]] = {
    val projection = BSONDocument("userDetails" -> 1, BSONDocument(
      "address" -> 1,
      "city" -> 1,
      "country" -> 1,
      "postalCode" -> 1,
      "phone" -> 1,
    ))
    mongoRepository.clientsCollection
      .flatMap(_
        .find(BSONDocument("user.username" -> username))
        // .projection(projection)
        .one[Client]).map(clientOption => clientOption.map(client => client.getUserDetails))
  }

  def findUserBasket(username: String): Future[Option[UserBasket]] = {
    mongoRepository.clientsCollection
      .flatMap(_
        .find(BSONDocument("user.username" -> username))
        .one[Client])
      .map(clientOption => clientOption.map(client => client.getUserBasket))
  }

  def updateUser(username: String, user: User): Unit = {
    mongoRepository.clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.username" -> username),
        u = BSONDocument("$set" -> BSONDocument(
          "user" -> user
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }

  def updateUserDetails(username: String, userDetails: UserDetails): Unit = {
    mongoRepository.clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.username" -> username),
        u = BSONDocument("$set" -> BSONDocument(
          "userDetails" -> userDetails
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }

  def updateUserBasket(username: String, userBasket: UserBasket): Unit = {
    mongoRepository.clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.username" -> username),
        u = BSONDocument("$set" -> BSONDocument(
          "userBasket" -> userBasket
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }

  def deleteUserBasket(username: String): Unit = {
    mongoRepository.clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.username" -> username),
        u = BSONDocument("$set" -> BSONDocument(
          "userBasket" -> UserBasket(List(), 0.0)
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }
}
