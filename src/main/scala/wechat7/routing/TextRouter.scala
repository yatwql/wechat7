package wechat7.routing
import wechat7.util._
class TextRouter extends Router {
  override def response(fromUser:String,appUserId:String,msgType:String,requestContent:String):String = {
    requestContent match{
       case "news" =>  WechatUtils.getNewsMsg(appUserId,fromUser,"PK news")
       case _  =>  {
        val responseContent= " Thanks for your information '" + requestContent + "' with msg type " + msgType
         WechatUtils.getTextMsg(appUserId,fromUser,responseContent)
       }
     } 
  }
}