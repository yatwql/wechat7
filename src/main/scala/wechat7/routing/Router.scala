package wechat7.routing

import wechat7.util._
import scala.slick.driver.JdbcProfile
import wechat7.persistent._

import scala.xml._
class Router extends SlickRepo with UserRepo with VoteRepo {
 import profile.simple._

  def response(requestXml: Option[Elem]): Node = {
    val appUserId = (requestXml.get \ "ToUserName").text
    val openId = (requestXml.get \ "FromUserName").text
    val requestContent = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text
    val requestXmlContent = requestXml.toString
    println("Get Message Type  " + msgType + " from user " + openId)
    audit(openId, appUserId, msgType, requestXmlContent)
    val responseXml = responseImpl(openId, appUserId, msgType, requestXml, requestContent)
    val responseContent=responseXml.toString()
    val responseMsgType=(responseXml \\ "MsgType").text
    audit(appUserId, openId, responseMsgType, responseContent)
    responseXml
  }

  def responseImpl(openId: String, appUserId: String, msgType: String, requestXml:  Option[Elem], requestContent: String): Node = {
    val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
    WechatUtils.getTextMsg(appUserId, openId, responseContent);
  }
  
  override def getNickname(openId:String):Option[String]={
    val s=super.getNickname(openId)
    val nickname=s match {
      case Some(t) => t
      case None => {
         addUser(WechatUtils.getUserInfo(openId))
      }
      case _ => "not found"
    }
    Some(nickname)
  }

  

}

object Rounter {
  def response(requestXml: Option[Elem]): String = {

    val msgType = (requestXml.get \ "MsgType").text

    val rounter: Router =
      msgType match {
        case Constants.REQ_MSG_TYP_TEXT => new TextRouter
        case Constants.REQ_MSG_TYP_IMAGE => new ImageRouter
        case Constants.REQ_MSG_TYP_VOICE => new VoiceRouter
        case Constants.REQ_MSG_TYP_VIDEO => new VideoRouter
        case Constants.REQ_MSG_TYP_LOCATION => new LocationRouter
        case Constants.REQ_MSG_TYP_LINK => new LinkRouter
        case Constants.REQ_MSG_TYP_EVENT => new EventRouter
        case _ => new DefaultRouter
      }

    rounter.response(requestXml).toString()

  }
}