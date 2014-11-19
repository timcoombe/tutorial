package controllers

import anorm._
import controllers.Application._
import controllers.routes
import models.Person
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.mvc._

import scala.text


object Admin extends Controller{


  val courseForm : Form[Course] = Form {
    mapping(
      "name" -> text
    )(Course.apply)(Person.unapply)
  }



  def addCourse() = Action {implicit request =>

    DB.withConnection { implicit c =>
      val person = personForm.bindFromRequest.get
      SQL("insert into Person(name) values ({name})")
        .on("name" -> person.name).executeInsert()
    }

    Redirect(routes.Application.index())


  }

}
