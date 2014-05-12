package wechat7.act

import scala.xml.Node

import spray.caching.Cache
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo._
import wechat7.util._
import wechat7.util.WechatUtils
import scala.concurrent._

import ExecutionContext.Implicits.global

trait VotePlugin extends VoteRepo with Plugin {
  
  def splitVoteOptions(list:List[VoteOptions#TableElementType]):Option[(String,Seq[String])]={
    list match{
      case a::rest => {
        val option=a.option
        val optionDesc=a.optionDesc
        splitVoteOptions(rest) match {
          case Some((restDesc,restSeq)) =>Some((option+":"+optionDesc+"; "+restDesc,restSeq :+option))
          case  _ => Some((option+":"+optionDesc,Seq(option)))
        }
      }
      case _=> None
    }
  }

  def getVoteThreadFromCache(voteId: Int): Option[(String, String, Int,Seq[String])] = {
    Router.voteThreadCache(voteId) {
      val responseContent = getVoteThread(voteId)
      getVoteThread(voteId) match {
        case Some(voteThread) => {
          val (voteId,name, threadDesc, voteMethod) = (voteThread.voteId,voteThread.name, voteThread.description, voteThread.voteMethod)
          val list:List[VoteOptions#TableElementType]=this.getVoteOptions(voteId)
          val (description,voteOptions) =splitVoteOptions(list) match {
            case Some((s,t)) => (threadDesc+" - "+s,t)
            case _ => (threadDesc,Seq())
          }
          Some((name, description, voteMethod,voteOptions))
        }
        case _ => None
      }
    }.await
  }
  def vote(openId: String, nickname: String, appUserId: String, voteId: Int, requestContent: String): Option[Node] = {

    addVoteResult(openId, voteId, requestContent)
    val responseContent = getVoteThreadFromCache(voteId) match {
      case Some((voteName,description,voteMethod,voteOptions)) => nickname + ", 欢迎参加  '" + voteName+ "' , " + description
      case _ => nickname + " ,vote '" + requestContent + "' to a invalide vote id  " + voteId
    }
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }

  def voting(openId: String, nickname: String, appUserId: String, voteId: Int, requestContent: String): Option[Node] = {

    updateVoteResult(openId, voteId, requestContent)

    val responseContent = getVoteThread(voteId) match {
      case Some(voteThread) => nickname + ", 您投了   '" + requestContent + "' 给 '" + voteThread.name + "'"
      case _ => nickname + " ,voting '" + requestContent + "' to a invalide vote id  " + voteId
    }
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))

  }
}