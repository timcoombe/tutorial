package models

import anorm._
import org.json4s.native.Serialization._
import play.api.db.DB
import play.api.Play.current
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}


import scala.collection.mutable

trait LessonPart {

}

object LessonPart {

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[LessonPartParagraph])))


  def delete(id: Long): Unit = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          DELETE FROM LessonPart where lesson_part_id = {id}
            """).on(
            'id ->
              id
        ).
        executeUpdate
      }
    }


  def getLessonPart(id:Long) : Option[LessonPart] = {

    DB.withConnection { implicit c =>

      val parts =  new mutable.MutableList[LessonPart]()
      val result = SQL("select * from LessonPart where lesson_part_id = {id}").on(
        'id -> id
      )

      result().map {row =>
        val lesson_part_id = row[Long]("lesson_part_id")
        val json = row[String]("json")

        var lp: LessonPart  = read(json)

        lp match {
          case paragraph: LessonPartParagraph  => {
            parts+= LessonPartParagraph(lesson_part_id, paragraph.lesson_id,paragraph.headline, paragraph.text)
          }
          case _ =>
        }
      }

      if (!parts.isEmpty) {
        Some(parts.head)
      } else {
        None
      }
    }
  }
}
