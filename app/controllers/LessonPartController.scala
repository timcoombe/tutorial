package controllers

import controllers.CourseController._
import controllers.LessonController._
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

object LessonPartController extends Controller{

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[LessonPartParagraph])))


  val lessonParagraphForm : Form[LessonPartParagraph] = Form {
    mapping(
      "part_id" -> longNumber,
      "lesson_id" -> longNumber,
      "headline" -> text,
      "text" -> text
    )(LessonPartParagraph.apply)(LessonPartParagraph.unapply)
   }

  def getLessonParts(id: Long) = Action {

    val lessonParts = Lesson.getLessonParts(id).toList

    Ok(write(lessonParts)).as("application/json")

  }

  def lessonPart(id: Long) = Action {

    val lessonPart = LessonPart.getLessonPart(id)

    lessonPart match {
      case Some(part) => {
        part match {
          case s: LessonPartParagraph => {
            val paragraph = part.asInstanceOf[LessonPartParagraph]
            Ok(views.html.adminlessonparagraph(paragraph)(getLessonPartBreadCrumb(part)))
          }
          case _ => NotFound
        }
      }
      case None => NotFound
    }

  }

  def getLessonPartBreadCrumb(lessonPart:LessonPart) : BreadCrumb = {

    lessonPart match {
      case para: LessonPartParagraph => {
          val breadCrumb = new BreadCrumb()
          breadCrumb.addLink("Courses","/admin")
          val lesson: Lesson = Lesson.getLesson(para.lesson_id)
          breadCrumb.addLink(Course.getCourse(lesson.course_id).name,s"/admin/course/${lesson.course_id}")
          breadCrumb.addLink(lesson.name,s"/admin/lesson/${lesson.lesson_id}")
          breadCrumb
      }
      case _ => new BreadCrumb

    }

  }

  def newLessonPart(lesson_id: Long, partType: String) = Action {


    println("partType: " + partType)

    partType match{
      case "paragraph" => {
        val breadCrumb = new BreadCrumb()
        Ok(views.html.adminlessonparagraph(new LessonPartParagraph(0, lesson_id, "", ""))(breadCrumb))
      }
      case _ => NotFound
    }

  }

  def addLessonParagraph(lesson_id: Long) = Action {implicit request =>

    val paragraph = lessonParagraphForm.bindFromRequest.get

    LessonPartParagraph.addLessonPart(paragraph)

    val breadCrumb = new BreadCrumb()
    Ok(views.html.adminlesson(lesson_id)(breadCrumb))

  }

  def delete(id: Long) = Action {

    LessonPart.delete(id)

    Ok
  }

}
