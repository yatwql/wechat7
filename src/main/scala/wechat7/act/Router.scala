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

trait Plugin extends ArticleRepo{
  val system = ActorSystem()
  import system.dispatcher
    def getNextAction(actionKey: String): Option[String] = {
    Router.actionCache(actionKey) {
      //this.getAction(actionKey)
      val action=getAction(actionKey)
      action.nextAction match {
        case "" => None
        case _ => Some(action.nextAction)
      }
    }.await
  }
  
  def getUserAction(openId:String):Option[String] ={
  val f= Router.userActions.get(openId)
 // f.get.await
  
  f match {
    case None => None
    case _ => f.get.await
  }
  }
  
  def updateUserAction(openId:String,actionKey:String) ={
    Router.userActions(openId) {
      getNextAction(actionKey)
    }
  }
  def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String) {

  }
}

object Router {
  val nicknames: Cache[Option[String]] = LruCache(maxCapacity = 300)
  val userActions: Cache[Option[String]] = LruCache(maxCapacity = 2000)
  val actionCache: Cache[Option[String]] = LruCache(maxCapacity = 50)
  val articleCache: Cache[Seq[Node]] = LruCache(maxCapacity = 100)
  def response(requestXml: Option[Elem]): String = {

    val msgType = (requestXml.get \ "MsgType").text

    val agent: Agent =
      msgType match {
        case Constants.REQ_MSG_TYP_TEXT => new TextAgent
        case Constants.REQ_MSG_TYP_IMAGE => new ImageAgent
        case Constants.REQ_MSG_TYP_VOICE => new VoiceAgent
        case Constants.REQ_MSG_TYP_VIDEO => new VideoAgent
        case Constants.REQ_MSG_TYP_LOCATION => new LocationAgent
        case Constants.REQ_MSG_TYP_LINK => new LinkAgent
        case Constants.REQ_MSG_TYP_EVENT => new EventAgent
        case _ => new DefaultAgent
      }

    agent.process(requestXml).toString()

  }
}
