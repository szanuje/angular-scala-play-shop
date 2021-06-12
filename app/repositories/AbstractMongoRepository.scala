package repositories

import play.api.Configuration
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractMongoRepository(implicit ec: ExecutionContext, config: Configuration,
                                       reactiveMongoApi: ReactiveMongoApi) {

  def productsCollection: Future[BSONCollection] = {
    reactiveMongoApi.database.map(_.collection("products"))
  }

  def clientsCollection: Future[BSONCollection] = {
    reactiveMongoApi.database.map(_.collection("clients"))
  }

}
