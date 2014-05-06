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
class Agent extends SlickRepo with AdminRepo with UserRepo with VoteRepo with ArticleRepo {
  import profile.simple._
  val system = ActorSystem()
  import system.dispatcher

  def process(requestXml: Option[Elem]): Option[Node] = {
    val appUserId = (requestXml.get \ "ToUserName").text
    val openId = (requestXml.get \ "FromUserName").text
    val requestContent = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text
    val requestXmlContent = requestXml.toString
    println("Get Message Type  " + msgType + " from user " + openId)
    audit(openId, appUserId, msgType, requestXmlContent)
    val responseXml = process(openId, appUserId, msgType, requestXml, requestContent)
    val responseContent = responseXml.get.toString()
    val responseMsgType = (responseXml.get \\ "MsgType").text
    audit(appUserId, openId, responseMsgType, responseContent)
    responseXml
  }

  def process(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Option[Node] = {
    val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent));
  }


  def getNicknameFromDB(openId: String): Option[String] = {
    println(" Visit DB to get nickname for openid " + openId)
    val s = super.getNickname(openId)
    val nickname = s match {
      case Some(t) => t
      case None => {
        println(" Get user info from wechat site")
        addUser(WechatUtils.getUserInfo(openId))
      }
      case _ => "not found"
    }
    Some(nickname)
  }

  override def getNickname(openId: String): Option[String] = {
    println(" Get nickname for openid " + openId)
    Rounter.nicknames(openId) {
      getNicknameFromDB(openId)
    }.await()
  }
}
class DefaultAgent extends Agent{

}
