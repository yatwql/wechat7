package wechat7.act

import scala.xml._

import wechat7.util._
class EventAgent extends Agent with ActionRouter {

  override def process(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Option[Node] = {
    import wechat7.util.Constants
    val event = (requestXml.get \ "Event").text
    val nickname = getNickname(openId).get
    val responseContent = nickname + "  love redwine for event " + event

    event match {
      case Constants.EVT_TYP_SUBSCRIBE => {
        updateSubscriptStatus(openId, 1)
        val item1 = <item>
                      <Title>Welcome you - { nickname }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>{ Constants.REDWINE_PIC }</PicUrl>
                      <Url>{ Constants.SHOP_AT_DIANPING }</Url>
                    </item>

        val items = Seq(item1)
        val node = WechatUtils.getNewsMsg(appUserId, openId, items)
        Some(node)
      }
      case Constants.EVT_TYP_UNSUBSCRIBE => {
        Router.nicknames.remove(openId)
        updateSubscriptStatus(openId, 0)
        None
      }
      case Constants.EVT_TYP_SCAN => Some(WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING));
      case Constants.EVT_TYP_LOCATION => Some(WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING));
      case Constants.EVT_TYP_CLICK => {
        val eventKey = (requestXml.get \ "EventKey").text
        //addVoteResult(openId, 1, eventKey)
        response(openId, nickname, appUserId, msgType, eventKey)
      }
      case Constants.EVT_TYP_VIEW => None
      case _ => None;
    }
  }
}
