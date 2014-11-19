package controllers

import models.Course
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.mvc._
import anorm._
import play.api.Play.current


object Admin extends Controller{


  val courseForm : Form[Course] = Form {
    mapping(
      "name" -> text
    )(Course.apply)(Course.unapply)
  }



  def addCourse = Action {implicit request =>

    DB.withConnection { implicit c =>
      val course = courseForm.bindFromRequest.get
      SQL("insert into Person(name) values ({name})")
        .on("name" -> course.name).executeInsert()
    }

    Redirect(routes.Application.index())

  }

}
