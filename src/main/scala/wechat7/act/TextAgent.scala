package wechat7.act
import wechat7.util._
import scala.xml._
import scala.collection._
class TextAgent extends Agent with ActionRouter {

  override def go(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Option[Node] = {
    val nickname = getNickname(openId).get
    response(openId, nickname, appUserId, msgType, requestContent, requestContent)
  }

}
