package wechat7.act
import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo._
import wechat7.util.WechatUtils
trait ActionPlugin extends ArticleRepo with VoteRepo with Plugin {
  override def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String) = {
    super.process(openId, nickname, appUserId, msgType, actionKey)
    println(" process from ActionPlugin ")
    val action = getUserAction(openId)
    val Pattern = "vote(\\d+)".r
     println(" process from ActionPlugin -user  action "+action )
    action match {
      case Pattern(voteId) => {
        println(" vote " + voteId + " from user " + openId)
        addVoteResult(openId, voteId.toInt, actionKey)
      }
      case _ => {

      }
    }

    updateUserAction(openId, actionKey)

  }
}