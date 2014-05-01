package wechat7.routing

import wechat7.util._
import scala.slick.driver.JdbcProfile
import wechat7.persistent._

import scala.xml._
class Router extends SlickRepo  {
 import profile.simple._

  def response(requestXml: Option[Elem]): Node = {
    val appUserId = (requestXml.get \ "ToUserName").text
    val fromUser = (requestXml.get \ "FromUserName").text
    val requestContent = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text
    val requestXmlContent = requestXml.toString
    println("Get Message Type  " + msgType + " from user " + fromUser)
    audit(fromUser, appUserId, msgType, requestXmlContent)
    val responseXml = responseImpl(fromUser, appUserId, msgType, requestXmlContent, requestContent)
    val responseContent=responseXml.toString()
    val responseMsgType=(responseXml \\ "MsgType").text
    audit(appUserId, fromUser, responseMsgType, responseContent)
    responseXml
  }

  def responseImpl(fromUser: String, appUserId: String, msgType: String, requestXmlContent: String, requestContent: String): Node = {
    val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
    WechatUtils.getTextMsg(appUserId, fromUser, responseContent);
  }

  def audit(fromUser: String, toUser: String, msgType: String, requestXmlContent: String): Unit = {
    conn.dbObject withSession { implicit session: Session =>
      auditLogs.insert(AuditLog(fromUser, toUser, msgType, requestXmlContent))
    }
  }

}

object Rounter {
  def response(requestXml: Option[Elem]): String = {

    val msgType = (requestXml.get \ "MsgType").text

    val rounter: Router =
      msgType match {
        case Constants.REQ_MESSAGE_TYPE_TEXT => new TextRouter
        case Constants.REQ_MESSAGE_TYPE_IMAGE => new ImageRouter
        case Constants.REQ_MESSAGE_TYPE_VOICE => new VoiceRouter
        case Constants.REQ_MESSAGE_TYPE_VIDEO => new VideoRouter
        case Constants.REQ_MESSAGE_TYPE_LOCATION => new LocationRouter
        case Constants.REQ_MESSAGE_TYPE_LINK => new LinkRouter
        case Constants.REQ_MESSAGE_TYPE_EVENT => new EventRouter
        case _ => new DefaultRouter
      }

    rounter.response(requestXml).toString()

  }
}