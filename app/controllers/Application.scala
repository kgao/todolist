package controllers

import play.api._

import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.Task



object Application extends Controller {
  
  def index = Action {
   // Ok(views.html.index("Hello Play World!"))
  //  Ok("Hello World ! - Kev")
    
     // Redirect to /hello/tasks
	  Redirect(routes.Application.tasks)
  }
  
   // Redirect to /hello/Kev
//  def helloKev = Action {
 //   Redirect(routes.Application.hello("Kev"))    
//  }
 
  def tasks = Action{
    //Ok("Hello World ! - Kev")
    Ok(views.html.index(Task.all(), taskForm))

  }  
  
	def newTask =  Action { implicit request =>
	  taskForm.bindFromRequest.fold(
	    errors => BadRequest(views.html.index(Task.all(), errors)),
	    label => {
	      Task.create(label)
	      Redirect(routes.Application.tasks)
	    }
	  )
	}

def deleteTask(id: Long) = Action {
  Task.delete(id)
  Redirect(routes.Application.tasks)
}


  def hello(name: String) = Action {
      Ok("Hello " + name + "!")
  }
  
  
  val taskForm = Form{
    "label"->nonEmptyText
  }
 


}