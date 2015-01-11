package models

import anorm._
import play.api.db.DB
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import play.api.Play.current

case class LessonPartParagraph(lesson_part_id: Long, lesson_id: Long, headline: String, text:String) extends LessonPart {


}

object LessonPartParagraph {

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[LessonPartParagraph])))

  def addLessonPart(lessonPart: LessonPart) = {

    lessonPart match {
      case paragraph: LessonPartParagraph => {

        val json = write(paragraph)

        DB.withConnection { implicit c =>

          SQL("insert into LessonPart (lesson_id, json) values ({lesson_id},{json})")
            .on("lesson_id" -> paragraph.lesson_id, "json" -> json).executeInsert()
        }
      }
      case _ =>
    }
  }

}

