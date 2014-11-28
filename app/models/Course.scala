package models

import play.api.libs.json.Json
import play.api.db.DB
import anorm._
import play.api.Play.current

case class Course(course_id:Long,name: String){
  def this(name: String) = this(0,name)
}

object Course{

  implicit val courseFormat = Json.format[Course]

  //JSON parser doesn't like multiple apply/unapply methods
 /* def my_apply(name: String) = new Course(name)
  def my_unapply(course: Course) = Some(course.name)
*/


  def addCourse(name: String) = {

    DB.withConnection { implicit c =>

      SQL("insert into Course(name) values ({name})")
        .on("name" -> name).executeInsert()
    }
  }


  def getCourses = {

    DB.withConnection { implicit c =>

      val selectCourses = SQL("select * from Course")

      selectCourses().map {row =>
        Course(row[Long]("course_id"),row[String]("name"))
      }.toList

    }
  }


  def deleteCourse(id: Long){

     DB.withConnection { implicit connection =>
      SQL("""
          DELETE FROM Course where course_id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }

  }


}

