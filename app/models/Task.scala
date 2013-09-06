package models
import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String)

object Task {
  
  	//It’s now time to implement the SQL queries in the Task companion object
	val task = {
	  get[Long]("id") ~ 
	  get[String]("label") map {
	    case id~label => Task(id, label)
	  }
	}
	
	//We can now use this parser to write the all() method implementation:
	//We use the Play DB.withConnection helper to create and release automatically a JDBC connection. 
	def all(): List[Task] = DB.withConnection { implicit c =>
	  SQL("select * from task").as(task *)
	}

	//Then we use the Anorm SQL method to create the query.
	//The as method allows to parse the ResultSet using the task * parser: 
	//it will parse as many task rows as possible and then return a List[Task] 

def create(label: String) {
  DB.withConnection { implicit c =>
    SQL("insert into task (label) values ({label})").on(
      'label -> label
    ).executeUpdate()
  }
}

def delete(id: Long) {
  DB.withConnection { implicit c =>
    SQL("delete from task where id = {id}").on(
      'id -> id
    ).executeUpdate()
  }
}
  
  
}