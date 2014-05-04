package wechat7.persistent

trait AdminRepo extends SlickRepo {
  import profile.simple._
  def audit(fromUser: String, toUser: String, msgType: String, requestXmlContent: String): Unit = {
    conn.dbObject withSession { implicit session: Session =>
      auditLogs.insert(AuditLog(fromUser, toUser, msgType, requestXmlContent))
    }
  }

  def loadLatestMenu() :Option[String]= {
    conn.dbObject withSession { implicit session: Session =>
      val query = settings.filter(_.name.===("menu")).map(_.content)
      val result = query.list()
      Some(result.last)
    }
  }
}