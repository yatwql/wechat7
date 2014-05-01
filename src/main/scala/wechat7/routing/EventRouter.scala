package wechat7.routing

import scala.xml._

import wechat7.util._
class EventRouter extends Router {

  override def responseImpl(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Node = {
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
        node
      }
      case Constants.EVT_TYP_UNSUBSCRIBE => {
        val item1 = <item>
                      <Title>bye bye- { nickname }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>{ Constants.REDWINE_PIC }</PicUrl>
                      <Url>{ Constants.SHOP_AT_DIANPING }</Url>
                    </item>

        val items = Seq(item1)
        val node = WechatUtils.getNewsMsg(appUserId, openId, items)
        updateSubscriptStatus(openId, 0)
        node
      }
      case Constants.EVT_TYP_SCAN => WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_LOCATION => WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_CLICK => {
        val eventKey = (requestXml.get \ "EventKey").text
        addVoteResult(openId, 1, eventKey)
        WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      }
      case Constants.EVT_TYP_VIEW => WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case _ => WechatUtils.getNewsMsg(appUserId, openId, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
    }

  }
}