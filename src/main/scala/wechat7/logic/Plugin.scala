package wechat7.logic

import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.agent.Router
import wechat7.repo.ActionRepo
import wechat7.repo.UserRepo
import wechat7.util.WechatUtils

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
    val action = Router.userActionCache.get(openId)

    action match {
      case None => None
      case _ => action.get.await
    }
  }

  def updateUserAction(openId: String, actionKey: String) = {
    println(" prepare to update the next action of " + openId + " to " + getNextAction(actionKey))
    Router.userActionCache(openId) {
      val action = getNextAction(actionKey)
      action match {
        case Some("ignore") => None
        case Some("") => None
        case Some(action1) => {
          println(" Update the next action of " + openId + " to " + action)
          action
        }
        case _ => None
      }

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
    Router.nicknameCache(openId) {
      getNicknameFromDB(openId)
    }.await()
  }
  def process(openId: String, nickname: String, appUserId: String, msgType: String, currentAction: Option[String], requestContent: String): Option[Node] = {
    None
  }

  def dontknow(openId: String, appUserId: String, nickname: String, requestContent: String): Option[Node] = {
    val responseContent = nickname + " ,没能理解您的意思 '" + requestContent + "', 输入 help 可获取帮助  "
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }

  def splitListIntoDesc(list: List[(String, String)]): Option[String] = {
    list match {
      case data :: rest => {
        val (a, b) = data
        splitListIntoDesc(rest) match {
          case Some((restDesc)) => Some((a + "," + b + "; " + restDesc))
          case _ => Some((a + " , " + b))
        }
      }
      case _ => None
    }
  }
}