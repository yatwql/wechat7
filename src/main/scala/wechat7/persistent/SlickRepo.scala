package wechat7.persistent
import scala.slick.driver.JdbcProfile
import wechat7.util._
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
        accounts.ddl.drop
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
      
       try {
        userStates.ddl.drop
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      
      
      "Table decreation - OK"
    }
  }

  def createTables: String = {
    conn.dbObject withSession { implicit session: Session =>
      try {
        accounts.ddl.create
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
      
       try {
        userStates.ddl.create
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
      accounts.insert(Account("stallman", "Stallman", "stallman.wang@foxmail.com", "stallman", "admin"))
      accounts.insert(Account("yatwql", "joe", "yatwql@qq.com", "yatwql", "admin"))
      accounts.insert(Account("test", "joe", "yatwql@qq.com", "test", "admin"))
      println("======================retrieve from database ====================")
      accounts.list foreach println
      // delete
      val query = for { emp <- accounts if (emp.name === "test") } yield emp
      query.delete
      println("======================retrieve after delete ====================")
      accounts.list foreach println
      voteTopics.insert(VoteTopic("redwine","Favour contry"))
      
      articles.insert(Article("New Title 1","Description","1","text"))
      articles.insert(Article("New Title 2A","New Description 2A","2","news",Constants.REDWINE_PIC,Constants.SHOP_AT_DIANPING))
      articles.insert(Article("New Title 2B","New Description 2B","2","news",Constants.REDWINE_PIC,Constants.SHOP_AT_DIANPING))
       articles.insert(Article("New Title 3","New Description","3","news",Constants.REDWINE_PIC,Constants.SHOP_AT_DIANPING))
       articles.insert(Article("Helps","This is for helping","?","news",Constants.REDWINE_PIC,Constants.SHOP_AT_DIANPING))
        articles.insert(Article("Helps","This is for helping","help","news",Constants.REDWINE_PIC,Constants.SHOP_AT_DIANPING))
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
