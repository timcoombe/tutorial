package models

import anorm._
import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import play.api.db.DB
import play.api.libs.json.Json
import play.api.Play.current
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

import scala.collection.mutable

case class Lesson(lesson_id: Long,course_id:Long,name:String,description:String) {



}
object Lesson {

  implicit val lessonFormat = Json.format[Lesson]
  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[LessonPartParagraph])))

  def addLesson(courseId: Long, name: String, description: String) = {

    DB.withConnection { implicit c =>

      SQL("insert into Lesson (course_id, name, description) values ({course_id},{name},{description})")
        .on("course_id" -> courseId,"name" -> name, "description" -> description).executeInsert()
    }
  }

  def getLessons(course_id: Long) = {

    DB.withConnection { implicit c =>

      val selectLesson = SQL("select * from Lesson where course_id = {id}").on(
        'id -> course_id
      )

      selectLesson().map {row =>
        Lesson(row[Long]("lesson_id"),row[Long]("course_id"),row[String]("name"),row[String]("description"))
      }.toList
    }

  }

  def deleteLesson(id: Long) = {

    DB.withConnection { implicit connection =>
      SQL("""
          DELETE FROM Lesson where lesson_id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }

  }

  def getLesson(id: Long) = {

    DB.withConnection { implicit c =>

      val selectLesson = SQL("select * from Lesson where lesson_id = {id}").on(
        'id -> id
      )

      selectLesson().map {row =>
        Lesson(row[Long]("lesson_id"),row[Long]("course_id"),row[String]("name"),row[String]("description"))
      }.toList(0)
    }
  }


  def updateLesson(lesson: Lesson) = {

    DB.withConnection { implicit c =>

      SQL("update Lesson set name = {name}, description = {description} where lesson_id = {id}")
        .on("name" -> lesson.name, "description" -> lesson.description, "id" -> lesson.lesson_id).executeUpdate()
    }


  }

  def getLessonParts(id: Long) : mutable.MutableList[LessonPart] = {

    val parts =  new mutable.MutableList[LessonPart]()

    DB.withConnection { implicit c =>

      val selectLesson = SQL("select * from LessonPart where lesson_id = {id}").on(
        'id -> id
      )

      selectLesson().foreach {row =>
        val lesson_part_id = row[Long]("lesson_part_id")
        val json = row[String]("json")

        var lp: LessonPart  = read(json)

        lp match {
          case paragraph: LessonPartParagraph  => {
            parts += LessonPartParagraph(lesson_part_id, paragraph.lesson_id,paragraph.headline, paragraph.text)
          }
          case _ =>
        }
      }
    }

    parts

  }

}
