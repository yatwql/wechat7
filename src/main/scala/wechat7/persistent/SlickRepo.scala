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
//import scala.slick.lifted.TableQuery
class SlickRepo(override val profile: JdbcProfile = SlickDBDriver.getDriver) extends DomainComponent with Profile {
  implicit val formats = DefaultFormats
  import profile.simple._
  val conn = new DBConnection(profile)

  def createTable(tableName: String) = {
    tables.get(tableName) match {
      case Some(table) => {
        conn.dbObject withSession { implicit session: Session =>
          try {
            accounts.ddl.create
          } catch {
            case ex: Exception => println(ex.getMessage)
          }
        }
      }

      case _ => None
    }

  }
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

      try {
        settings.ddl.drop
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

      try {
        settings.ddl.create
      } catch {
        case ex: Exception => println(ex.getMessage)
      }
      "Table creation - OK"
    }
  }

  def populate: String = {
    conn.dbObject withSession { implicit session: Session =>
      // create  table  selected environment

      println("======================Will insert data for accounts ====================")
      accounts.insert(Account("stallman", "Stallman", "stallman.wang@foxmail.com", "stallman", "admin"))
      accounts.insert(Account("yatwql", "joe", "yatwql@qq.com", "yatwql", "admin"))
      println("======================retrieve accounts from database ====================")
      accounts.list foreach println
      println("======================retrieve voteTopics from database ====================")
      voteTopics.insert(VoteTopic("redwine", "Favour contry"))
      voteTopics.list foreach println

      articles.insert(Article("题目1", "Description", "1", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
      articles.insert(Article("New Title 2A", "New Description 2A", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
      articles.insert(Article("题目2", "New Description 2B", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
      articles.insert(Article("New Title 3", "New Description", "3", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
      articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "help", "text"))
      println("======================retrieve articles from database ====================")
      articles.list foreach println
      
      settings.insert(Setting("menu",WechatUtils.loadMenuFromFile))
      println("======================retrieve sysParams from database ====================")
      settings.list foreach println

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
