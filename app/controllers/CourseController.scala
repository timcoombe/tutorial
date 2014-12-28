package controllers

import models.Course
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

object CourseController extends Controller{



  val courseForm : Form[Course] = Form {
    mapping(
      "course_id" -> longNumber,
      "name" -> text,
      "description" -> text
    )(Course.apply)(Course.unapply)
  }

  def addCourse = Action {implicit request =>

    val course = courseForm.bindFromRequest.get
    Course.addCourse(course.name, course.description)

    Redirect(routes.Admin.index())

  }

  def updateCourse(id: Long) = Action {implicit request =>


    println("updating")

    val course = courseForm.bindFromRequest.get

    println(course.name)
    Course.updateCourse(course)

    Redirect(routes.Admin.index())

  }


  def courses = Action {

    val courses = Course.getCourses
    Ok(Json.toJson(courses))
  }


  def deleteCourse(id: Long) = Action {

    Course.deleteCourse(id)
    Ok("success")

  }


  def course(id: Long) = Action {

    println("blah blah")

    Ok(views.html.admincourse(id))

  }

  def getCourse(id: Long) = Action {

    val course = Course.getCourse(id)
    Ok(Json.toJson(course))

  }

}
