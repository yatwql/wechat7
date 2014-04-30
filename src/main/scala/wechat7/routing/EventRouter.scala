package wechat7.routing
import wechat7.util._
class EventRouter extends Router {
  override def response(fromUser:String,appUserId:String,msgType:String,requestContent:String):String = {
     val responseContent = " Thanks for your information '" + requestContent + "' this is news "
     WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
      }
}