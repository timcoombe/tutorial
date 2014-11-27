package controllers

import controllers.Application._
import models.Course
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.libs.json.Json
import play.api.mvc._
import anorm._
import play.api.Play.current


object Admin extends Controller{


  def index = Action {
    Ok(views.html.admin("Your new application is ready."))
  }



}
