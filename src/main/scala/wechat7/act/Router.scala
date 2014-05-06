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



object Rounter {
  val nicknames: Cache[Option[String]] = LruCache(maxCapacity = 300)
  val userStates: Cache[Option[String]] = LruCache(maxCapacity = 2000)
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
