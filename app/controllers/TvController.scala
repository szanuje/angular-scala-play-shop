package controllers

import models.Tv
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.TvService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TvController @Inject()(cc: ControllerComponents, tvService: TvService) extends AbstractController(cc) {
  implicit val tvWrites: OWrites[Tv] = Json.writes[Tv]
  implicit val tvReads: Reads[Tv] = Json.reads[Tv]

  def getTvs: Action[AnyContent] = Action.async { implicit request => {
    tvService.findAll()
      .map(res => {
        println("getTvs: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getTvById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    tvService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getTvById(" + id + "): not exists")
          BadRequest
        } else {
          println("getTvById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addTv(): Action[Tv] = Action.async(parse.json[Tv]) { implicit request => {
    val tv = request.body
    println("addTv: " + tv)
    tvService.findById(tv.uuid)
      .flatMap(_tv => {
        if (_tv.isEmpty) {
          println("tv added")
          tvService.create(tv)
          Future.successful(Created)
        } else {
          println("tv already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateTv(id: UUID): Action[Tv] = Action.async(parse.json[Tv]) { implicit request => {
    val tv = request.body
    println("updateTv: " + tv)
    tvService.update(id, tv)
    Future.successful(Created)
  }
  }

  def deleteTv(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteTv(" + id + ")")
    tvService.delete(id)
    Future.successful(NoContent)
  }
  }
}