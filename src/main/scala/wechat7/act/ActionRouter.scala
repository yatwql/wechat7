package wechat7.act

import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo.ActionRepo
import wechat7.util.WechatUtils

trait ActionRouter extends ActionRepo with Plugin with VotePlugin with ArticlePlugin {
  import system.dispatcher

  def process(openId: String, nickname: String, appUserId: String, currentAction: Option[String], requestContent: String): Option[Node] = {
    val votePattern = "vote(\\d+)".r
    val votingPattern = "voting(\\d+)".r
    val articlePattern = "(\\d+)".r
    currentAction match {
      case Some(actionKey) => {
        actionKey match {
          case "ignore" => {None}
          case "" => {None}
          case votePattern(voteId) => {
            vote(openId, nickname, appUserId, voteId.toInt, "")
          }
          case votingPattern(voteId) => {
            voting(openId, nickname, appUserId, voteId.toInt, requestContent)
          }

          case articlePattern(articleId) => {
            article(openId, nickname, appUserId, articleId, requestContent)
          }

          case _ => {
            dontknow(openId, appUserId, nickname, actionKey)
          }
        }
      }

      case _ => {
        println(" process from ActionPlugin - no  action")
        None
      }
    }

  }

  override def response(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String, requestContent: String): Option[Node] = {
    println(" process from ActionPlugin actionkey " + actionKey)
    val userAction = getUserAction(openId)
    println(" process from ActionPlugin user action " + userAction)

    val content = userAction match {
      case Some(action) => {
        val s = process(openId, nickname, appUserId, userAction, requestContent)
        Router.userActions.remove(openId)
        s
      }
      case _ => {
        val current = getCurrentAction(actionKey)
        println(" process from ActionPlugin currrent action " + current + " of action key " + actionKey)
        val t = process(openId, nickname, appUserId, current, requestContent)
        updateUserAction(openId, actionKey)
        t
      }
    }

    content

  }

}
