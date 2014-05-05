package wechat7.persistent
import wechat7.util._

trait AdminRepo extends SlickRepo {
  import profile.simple._
  override def createTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          createTable("auditLogs") + " , " + createTable("settings") + " , " + super.createTable(tableName)
        }
        case "auditLogs" => {
          try {
            auditLogs.ddl.create
            "auditLogs "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }
        case "settings" => {
          try {
            settings.ddl.create
            "settings "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case _ => super.createTable(tableName)
      }
    }
  }

  override def dropTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          dropTable("auditLogs") + " , " + dropTable("settings") + " , " + super.dropTable(tableName)
        }
        case "auditLogs" => {
          try {
            auditLogs.ddl.drop
            "auditLogs "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }
        case "settings" => {
          try {

            settings.ddl.drop
            "settings "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case _ => super.dropTable(tableName)
      }
    }
  }

  override def populateTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("auditLogs") + " , " + populateTable("settings") + " , " + super.populateTable(tableName)
        }

        case "settings" => {
          try {
            println("======================Insert settings into database ====================")
            settings.insert(Setting(Constants.MENU, WechatUtils.loadMenuFromFile))
            println("======================retrieve settings from database ====================")
            settings.list foreach println
            "settings "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case _ => super.populateTable(tableName)
      }
    }
  }

  def audit(fromUser: String, toUser: String, msgType: String, requestXmlContent: String): Unit = {
    conn.dbObject withSession { implicit session: Session =>
      auditLogs.insert(AuditLog(fromUser, toUser, msgType, requestXmlContent))
    }
  }

  def loadLatestMenu(): Option[String] = {
    conn.dbObject withSession { implicit session: Session =>
      val query = settings.filter(_.name.===(Constants.MENU)).map(_.content)
      val result = query.list()
      Some(result.last)
    }
  }

  def saveMenu(menu: String) {
    conn.dbObject withSession { implicit session: Session =>
      settings.insert(Setting(Constants.MENU, menu))
    }
  }
}