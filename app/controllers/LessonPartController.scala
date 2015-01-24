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

    Ok(views.html.adminlesson(id))

  }

  def newLessonPart(id: Long, partType: String) = Action {


    println("partType: " + partType)

    partType match{
      case "paragraph" => Ok(views.html.adminlessonparagraph(id,0))
      case _ => NotFound
    }

  }

  def addLessonParagraph(lesson_id: Long) = Action {implicit request =>

    val paragraph = lessonParagraphForm.bindFromRequest.get

    LessonPartParagraph.addLessonPart(paragraph)

    Ok(views.html.adminlesson(lesson_id))
  }

  def delete(id: Long) = Action {

    LessonPart.delete(id)

    Ok
  }

}
