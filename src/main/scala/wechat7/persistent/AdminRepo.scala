package wechat7.persistent

trait AdminRepo extends SlickRepo {
  import profile.simple._
  def audit(fromUser: String, toUser: String, msgType: String, requestXmlContent: String): Unit = {
    conn.dbObject withSession { implicit session: Session =>
      auditLogs.insert(AuditLog(fromUser, toUser, msgType, requestXmlContent))
    }
  }
}