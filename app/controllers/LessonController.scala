package controllers


import controllers.CourseController._
import models.{LessonPartParagraph, LessonPart, Course, Lesson}
import org.json4s.native.{Serialization, JsonMethods}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.data.Mapping
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import util.BreadCrumb

object LessonController extends Controller{

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[LessonPartParagraph])))


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

    val breadCrumb = new BreadCrumb()
    breadCrumb.addLink("Courses","/admin")

    val lesson: Lesson = Lesson.getLesson(id)
    Course.getCourse(Lesson.getLesson(id).course_id).name

    breadCrumb.addLink(Course.getCourse(lesson.course_id).name,s"/admin/course/${lesson.course_id}")

    Ok(views.html.adminlesson(id)(breadCrumb))

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
