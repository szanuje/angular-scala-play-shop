package controllers

import models.Watch
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.WatchService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class WatchController @Inject()(cc: ControllerComponents, watchService: WatchService) extends AbstractController(cc) {
  implicit val watchWrites: OWrites[Watch] = Json.writes[Watch]
  implicit val watchReads: Reads[Watch] = Json.reads[Watch]

  def getWatches: Action[AnyContent] = Action.async { implicit request => {
    watchService.findAll()
      .map(res => {
        println("getWatchs: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getWatchById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    watchService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getWatchById(" + id + "): not exists")
          BadRequest
        } else {
          println("getWatchById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addWatch(): Action[Watch] = Action.async(parse.json[Watch]) { implicit request => {
    val watch = request.body
    println("addWatch: " + watch)
    watchService.findById(watch.uuid)
      .flatMap(_watch => {
        if (_watch.isEmpty) {
          println("watch added")
          watchService.create(watch)
          Future.successful(Created)
        } else {
          println("watch already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateWatch(id: UUID): Action[Watch] = Action.async(parse.json[Watch]) { implicit request => {
    val watch = request.body
    println("updateWatch: " + watch)
    watchService.update(id, watch)
    Future.successful(Created)
  }
  }

  def deleteWatch(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteWatch(" + id + ")")
    watchService.delete(id)
    Future.successful(NoContent)
  }
  }
}