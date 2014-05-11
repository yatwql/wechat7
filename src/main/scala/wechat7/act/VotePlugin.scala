package wechat7.act
import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo._
import wechat7.util.WechatUtils
trait VotePlugin extends VoteRepo with Plugin {
def vote(openId: String, nickname: String, appUserId: String, voteId: Int,requestContent:String): Option[Node] = {
   
    addVoteResult(openId,voteId,requestContent)
 val responseContent = nickname + " ,vote '" + requestContent + "' to vote id  "+voteId
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }



def voting(openId: String, nickname: String, appUserId: String, voteId: Int,requestContent:String): Option[Node] = {
   
      updateVoteResult(openId,voteId,requestContent)
 val responseContent = nickname + " ,voting '" + requestContent + "' to vote id  "+voteId
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))

  }
}