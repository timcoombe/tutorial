package controllers


import controllers.CourseController._
import models.{Course, Lesson}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._


object LessonController extends Controller{

  val lessonForm : Form[Lesson] = Form {
    mapping(
      "lesson_id" -> longNumber,
      "course_id" -> longNumber,
      "name" -> text,
      "description" -> text
    )(Lesson.apply)(Lesson.unapply)
  }

  def addLesson = Action {implicit request =>

    val lesson = lessonForm.bindFromRequest.get
    Lesson.addLesson(lesson.course_id, lesson.name, lesson.description)

    Redirect(routes.CourseController.course(lesson.course_id))

  }

  def getLessons(course_id: Long) = Action {

    val lessons = Lesson.getLessons(course_id)
    Ok(Json.toJson(lessons))
  }

  def deleteLesson(id: Long) = Action {

    Lesson.deleteLesson(id)
    Ok("success")

  }


  def lesson(id: Long) = Action {

    Ok(views.html.adminlesson(id))

  }


  def getLesson(id: Long) = Action {

    val lesson = Lesson.getLesson(id)
    Ok(Json.toJson(lesson))

  }

  def updateLesson(id: Long) = Action {implicit request =>


    val lesson = lessonForm.bindFromRequest.get

    Lesson.updateLesson(lesson)

    Redirect(routes.Admin.index())

  }

}
