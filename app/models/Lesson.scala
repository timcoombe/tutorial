package models

import anorm._
import play.api.db.DB
import play.api.libs.json.Json
import play.api.Play.current

case class Lesson(lesson_id: Long,course_id:Long,name:String,description:String) {



}
object Lesson {

  implicit val lessonFormat = Json.format[Lesson]


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

}
