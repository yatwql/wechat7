package wechat7.routing

import wechat7.util._
import  scala.xml._
class Router {
  
   def response(fromUser:String,appUserId:String,msgType:String,requestContent:String):String = {
      val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
     WechatUtils.getTextMsg(appUserId, fromUser, responseContent);
   
  }
}

object Rounter{
  def response(requestXml: Option[Elem]):String = {
    val appUserId = (requestXml.get \ "ToUserName").text
    val fromUser = (requestXml.get \ "FromUserName").text
    val requestContent = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text
    
    val rounter:Router =
     msgType match {
      case Constants.REQ_MESSAGE_TYPE_TEXT => new TextRouter
      case Constants.REQ_MESSAGE_TYPE_IMAGE => new ImageRouter
      case Constants.REQ_MESSAGE_TYPE_VOICE => new VoiceRouter
      case Constants.REQ_MESSAGE_TYPE_VIDEO => new VideoRouter
      case Constants.REQ_MESSAGE_TYPE_LOCATION => new LocationRouter
      case Constants.REQ_MESSAGE_TYPE_LINK => new LinkRouter
      case Constants.REQ_MESSAGE_TYPE_EVENT =>  new EventRouter
      case _ => new Router
    }
    
    println("Get Message Type  " + msgType+" from user "+fromUser)
    rounter.response(fromUser, appUserId, msgType, requestContent)
     
  }
}