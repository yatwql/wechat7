package wechat7.act
import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo._
import wechat7.util.WechatUtils
trait ActionPlugin extends ActionRepo with VoteRepo with Plugin {
  override def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String, requestContent: String) = {
    super.process(openId, nickname, appUserId, msgType, actionKey, requestContent)
    println(" process from ActionPlugin actionkey "+actionKey)

    val userAction = getUserAction(openId)
    println(" process from ActionPlugin user action " + userAction)
    handle(openId, userAction, None, requestContent)
    Router.userActions.remove(openId)
    val current = getCurrentAction(actionKey)
    println(" process from ActionPlugin currrent action " + current +" of action key "+actionKey)
    handle(openId, current, userAction, requestContent)
    updateUserAction(openId, actionKey)

  }

  def handle(openId: String, currentAction: Option[String], previousAction: Option[String], requestContent: String) {
    val vote = "vote(\\d+)".r
    val voting = "voting(\\d+)".r
    //val pa = previousAction.get
    currentAction match {
      case Some(action) => {
        action match {
          case "ignore" => {}
          case "" => {}
          case vote(voteId) => {
            println(" vote " + voteId + " from user " + openId)
            addVoteResult(openId, voteId.toInt, "")
          }
          case voting(voteId) => {
            println(" Voting " + voteId + " from user " + openId)
            updateVoteResult(openId, voteId.toInt, requestContent)
          }

          case _ => {}
        }
      }
      //case previousAction => { println(" Same action as previous one,will ignore it") }
      case _ => println(" process from ActionPlugin - no  action")
    }

  }
}