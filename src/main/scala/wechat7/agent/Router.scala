package wechat7.agent

import scala.xml.Elem
import scala.xml.Node

import spray.caching.Cache
import spray.caching.LruCache
import wechat7.util.Constants

object Router {
  val nicknameCache: Cache[Option[String]] = LruCache(maxCapacity = 300)
  val userActionCache: Cache[Option[String]] = LruCache(maxCapacity = 2000)
  val nextActionCache: Cache[Option[String]] = LruCache(maxCapacity = 50)
  val currentActionCache: Cache[Option[String]] = LruCache(maxCapacity = 50)
  val articleCache: Cache[Seq[Node]] = LruCache(maxCapacity = 100)
  val voteThreadCache: Cache[Option[(String, String, Int, Seq[String])]] = LruCache(maxCapacity = 100)
  
  def response(requestXml: Option[Elem]): Option[String] = {

    val msgType = (requestXml.get \ "MsgType").text

    val agent: Agent =
      msgType match {
        case Constants.REQ_MSG_TYP_TEXT => new TextAgent
        case Constants.REQ_MSG_TYP_IMAGE => new DefaultAgent
        case Constants.REQ_MSG_TYP_VOICE => new DefaultAgent
        case Constants.REQ_MSG_TYP_VIDEO => new DefaultAgent
        case Constants.REQ_MSG_TYP_LOCATION => new DefaultAgent
        case Constants.REQ_MSG_TYP_LINK => new DefaultAgent
        case Constants.REQ_MSG_TYP_EVENT => new EventAgent
        case _ => new DefaultAgent
      }

    agent.go(requestXml) match {
      case Some(node) => Some(node.toString())
      case _ => None
    }

  }
}
