package models

import play.api.libs.json.Json

case class Course(course_id:Long,name: String){
  def this(name: String) = this(0,name)
}

object Course{

  implicit val courseFormat = Json.format[Course]

  //JSON parser doesn't like multiple apply/unapply methods
  def my_apply(name: String) = new Course(name)
  def my_unapply(course: Course) = Some(course.name)

}

