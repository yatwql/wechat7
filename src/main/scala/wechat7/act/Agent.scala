package wechat7.act
import wechat7.util._
import scala.concurrent.duration._
import scala.concurrent.Future
import akka.actor.ActorSystem
import spray.caching.{ LruCache, Cache }
import spray.util._

import scala.slick.driver.JdbcProfile
import wechat7.repo._

import scala.xml._
class Agent extends SlickRepo with AdminRepo with UserRepo with VoteRepo with ActionRepo with Plugin{
  import profile.simple._
  import system.dispatcher

  def go(requestXml: Option[Elem]): Option[Node] = {
    val appUserId = (requestXml.get \ "ToUserName").text
    val openId = (requestXml.get \ "FromUserName").text
    val requestContent = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text
    val requestXmlContent = requestXml.toString
    println("Get Message Type  " + msgType + " from user " + openId)
    audit(openId, appUserId, msgType, requestXmlContent)
    val responseXml = go(openId, appUserId, msgType, requestXml, requestContent)
    val responseContent = responseXml.get.toString()
    val responseMsgType = (responseXml.get \\ "MsgType").text
    audit(appUserId, openId, responseMsgType, responseContent)
    responseXml
  }

  def go(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Option[Node] = {
    val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent));
  }


 
}
class DefaultAgent extends Agent{

}
