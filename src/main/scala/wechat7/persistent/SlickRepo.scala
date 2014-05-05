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

  def createTable(tableName: String = "all"): String = {
    ""
  }

  def dropTable(tableName: String = "all") = {
    ""
  }

  def populateTable(tableName: String = "all") = {
    ""
  }

  def test: String = {
    dropTable("all")
    createTable("all")
    populateTable("all")
  }

}
object SlickRepoApp extends App {
  (new SlickRepo with AdminRepo with ArticleRepo with UserRepo with VoteRepo).test
}
