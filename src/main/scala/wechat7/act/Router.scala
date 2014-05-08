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

trait Plugin extends ActionRepo with UserRepo {
  import profile.simple._
  val system = ActorSystem()
  import system.dispatcher
  def getNextAction(actionKey: String): Option[String] = {
    Router.nextActionCache(actionKey) {
     val action = getAction(actionKey)
      action match {
        case Some(action1) => {
          action1.currentAction match {
            case "" => None
            case _ => Some(action1.nextAction)
          }
        }
        case _ => None
      }

    }.await
  }

  def getCurrentAction(actionKey: String): Option[String] = {
    Router.currentActionCache(actionKey) {
      val action = getAction(actionKey)
      action match {
        case Some(action1) => {
          action1.currentAction match {
            case "" => None
            case _ => Some(action1.currentAction)
          }
        }
        case _ => None
      }

    }.await
  }

  def getUserAction(openId: String): Option[String] = {
    val f = Router.userActions.get(openId)

    f match {
      case None => None
      case _ => f.get.await
    }
  }

  def updateUserAction(openId: String, actionKey: String) = {
    println(" prepare to update the next action of " + openId)
    Router.userActions(openId) {
      val action = getNextAction(actionKey)
      println(" Update the next action of " + openId + " to " + action)
      action
    }
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
    Router.nicknames(openId) {
      getNicknameFromDB(openId)
    }.await()
  }
  def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String, requestContent: String) {

  }
}

object Router extends ActionRepo {
  val nicknames: Cache[Option[String]] = LruCache(maxCapacity = 300)
  val userActions: Cache[Option[String]] = LruCache(maxCapacity = 2000)
  val nextActionCache: Cache[Option[String]] = LruCache(maxCapacity = 50)
  val currentActionCache: Cache[Option[String]] = LruCache(maxCapacity = 50)
  val articleCache: Cache[Seq[Node]] = LruCache(maxCapacity = 100)
  def response(requestXml: Option[Elem]): Seq[String] = {

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

    Seq(agent.go(requestXml).get.toString(), "test")

  }
}
