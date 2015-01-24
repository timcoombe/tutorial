package models

import anorm._
import play.api.db.DB
import play.api.Play.current

trait LessonPart {

}

object LessonPart {

    def delete(id: Long): Unit ={
      DB.withConnection { implicit connection =>
        SQL("""
          DELETE FROM LessonPart where lesson_part_id = {id}
            """).on(
            'id -> id
          ).executeUpdate
      }
    }

}
