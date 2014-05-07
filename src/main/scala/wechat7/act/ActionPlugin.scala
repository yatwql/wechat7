package wechat7.act
import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo._
import wechat7.util.WechatUtils
trait ActionPlugin extends ArticleRepo with VoteRepo with Plugin {
  override def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String, requestContent: String) = {
    super.process(openId, nickname, appUserId, msgType, actionKey, requestContent)
    println(" process from ActionPlugin ")

    val userAction = getUserAction(openId)
    println(" process from ActionPlugin user action " + userAction)
    handle(openId, userAction, requestContent)

    val current = getCurrentAction(actionKey)
    println(" process from ActionPlugin currrent action " + current)
    handle(openId, current, requestContent)
    updateUserAction(openId, actionKey)

  }

  def handle(openId: String, actionO: Option[String], requestContent: String) {
    val Pattern = "vote(\\d+)".r
    actionO match {
      case Some(action) => {
        action match {
          case Pattern(voteId) => {
            println(" vote " + voteId + " from user " + openId)
            addVoteResult(openId, voteId.toInt, requestContent)
          }
          case "ignore" => Router.userActions.remove(openId)
          case "" => Router.userActions.remove(openId)
          case _ => {

          }
        }
      }
      case _ => println(" process from ActionPlugin - no  action")
    }

  }
}