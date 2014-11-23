package controllers

import controllers.Application._
import models.Course
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.libs.json.Json
import play.api.mvc._
import anorm._
import play.api.Play.current


object Admin extends Controller{


  def index = Action {
    Ok(views.html.admin("Your new application is ready."))
  }


  val courseForm : Form[Course] = Form {
    mapping(
      "course_id" -> longNumber,
      "name" -> text
    )(Course.apply)(Course.unapply)
  }



  def addCourse = Action {implicit request =>

    DB.withConnection { implicit c =>
      val course = courseForm.bindFromRequest.get
      SQL("insert into Course(name) values ({name})")
        .on("name" -> course.name).executeInsert()
    }

    Redirect(routes.Application.index())

  }





  def courses = Action {

    val courses = DB.withConnection { implicit c =>

      val selectCourses = SQL("select * from Course")

      selectCourses().map {row =>
        Course(row[Long]("course_id"),row[String]("name"))
      }.toList

    }

    Ok(Json.toJson(courses))
  }

}
