package org.dragonstudio.wechat.persistent
import scala.slick.driver.JdbcProfile
import java.sql.Date
class SlickUtils(override val profile: JdbcProfile = SlickDBDriver.getDriver) extends DomainComponent with Profile {
  import profile.simple._
  val conn = new DBConnection(profile)
  def dropTables: Unit = {
    conn.dbObject withSession { implicit session: Session =>
      try {
        println(appUsers.getClass().getName())
        println(appUsers.ddl.getClass().getName())
        appUsers.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        articles.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        voteTopics.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        voteResults.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      
        try {
        messages.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
    }
  }
  
   def createTables: Unit = {
    conn.dbObject withSession { implicit session: Session =>
      try {
        appUsers.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        articles.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        voteTopics.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
       try {
        voteResults.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      
       try {
        messages.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
    }
  }
   
   def populate: Unit = {
      conn.dbObject withSession { implicit session: Session =>
      // create  table  selected environment
     

      // insert AppUser into database
      appUsers.insert(AppUser("stallman", "Stallman", "stallman.wang@foxmail.com", "stallman", "admin"))
      appUsers.insert(AppUser("yatwql", "joe", "yatwql@qq.com", "yatwql", "admin"))
      appUsers.insert(AppUser("test", "joe", "yatwql@qq.com", "test", "admin"))
      println("======================retrieve from database ====================")
      appUsers.list foreach println
      // delete
      val query = for { emp <- appUsers if (emp.name === "test") } yield emp
      query.delete
      println("======================retrieve after delete ====================")
      appUsers.list foreach println
    }
  }

  def flush: Unit = {
    dropTables
    createTables
    populate
  }
}
object SlickUtilsApp extends App {
  (new SlickUtils).flush
}
