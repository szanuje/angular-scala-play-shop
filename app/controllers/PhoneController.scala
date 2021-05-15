package controllers

import models.Phone
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.PhoneService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PhoneController @Inject()(cc: ControllerComponents, phoneService: PhoneService) extends AbstractController(cc) {
  implicit val phoneWrites: OWrites[Phone] = Json.writes[Phone]
  implicit val phoneReads: Reads[Phone] = Json.reads[Phone]

  def getPhones: Action[AnyContent] = Action.async { implicit request => {
    phoneService.findAll()
      .map(res => {
        println("getPhones: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getPhoneById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    phoneService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getPhoneById(" + id + "): not exists")
          BadRequest
        } else {
          println("getPhoneById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addPhone(): Action[Phone] = Action.async(parse.json[Phone]) { implicit request => {
    val phone = request.body
    println("addPhone: " + phone)
    phoneService.findById(phone.uuid)
      .flatMap(_phone => {
        if (_phone.isEmpty) {
          println("phone added")
          phoneService.create(phone)
          Future.successful(Created)
        } else {
          println("phone already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updatePhone(id: UUID): Action[Phone] = Action.async(parse.json[Phone]) { implicit request => {
    val phone = request.body
    println("updatePhone: " + phone)
    phoneService.update(id, phone)
    Future.successful(Created)
  }
  }

  def deletePhone(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deletePhone(" + id + ")")
    phoneService.delete(id)
    Future.successful(NoContent)
  }
  }
}