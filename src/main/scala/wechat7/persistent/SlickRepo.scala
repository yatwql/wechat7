package wechat7.persistent
import scala.slick.driver.JdbcProfile
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jvalue2extractable
import org.json4s._
import org.json4s.string2JsonInput
import java.sql.Timestamp
import java.sql.Date
class SlickRepo(override val profile: JdbcProfile = SlickDBDriver.getDriver) extends DomainComponent with Profile {
  implicit val formats = DefaultFormats
  import profile.simple._
  val conn = new DBConnection(profile)
  def dropTables: String = {
    conn.dbObject withSession { implicit session: Session =>
      try {
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
        auditLogs.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }

      try {
        users.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      "Table decreation - OK"
    }
  }

  def createTables: String = {
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
        auditLogs.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }

      try {
        users.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      "Table creation - OK"
    }
  }

  def populate: String = {
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
      voteTopics.insert(VoteTopic("redwine","Favour contry"))
      "population OK"
    }
  }

  def flush: String = {
    dropTables
    createTables
    populate
  }

  

  

}
object SlickRepoApp extends App {
  (new SlickRepo).flush
}
