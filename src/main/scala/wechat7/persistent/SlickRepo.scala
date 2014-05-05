package wechat7.persistent

import scala.slick.driver.JdbcProfile

import org.json4s.DefaultFormats

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

  def doAction(tableQuery: TableQuery[_ <: Table[_]], action: String): String = {
    conn.dbObject withSession { implicit session: Session =>
      try {
        action match {
          case "create" => {
            tableQuery.ddl.create
            "Create table " + tableQuery.baseTableRow.tableName + ";"
          }
          case "drop" => {
            tableQuery.ddl.drop
            "Drop table " + tableQuery.baseTableRow.tableName + ";"
          }
          case _ => ""
        }

      } catch {
        case ex: Exception => println(ex.getMessage); ""
      }
    }
  }

  def doCreate(tableQuery: TableQuery[_ <: Table[_]]): String = {
    doAction(tableQuery, "create")
  }

  def doDrop(tableQuery: TableQuery[_ <: Table[_]]): String = {
    doAction(tableQuery, "drop")
  }

}
object SlickRepoApp extends App {
  (new SlickRepo with AdminRepo with ArticleRepo with UserRepo with VoteRepo).test
}
