package wechat7.routing

import scala.xml._

import wechat7.util._
class EventRouter extends Router {

  override def responseImpl(fromUser: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Node = {
    import wechat7.util.Constants
    val event = (requestXml.get \ "Event").text
    val responseContent = " I love redwine for '" + requestContent + "' and event " + event

    event match {
      case Constants.EVT_TYP_SUBSCRIBE => {
        val nickname =addUser(WechatUtils.getUserInfo(fromUser))
        val item1 = <item>
                      <Title>Welcome you - { nickname }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>{Constants.REDWINE_PIC}</PicUrl>
                      <Url>{Constants.SHOP_AT_DIANPING}</Url>
                    </item>

        val items = Seq(item1)
        val node = WechatUtils.getNewsMsg(appUserId, fromUser, items)    
        node
      }
      case Constants.EVT_TYP_UNSUBSCRIBE => {
        val item1 = <item>
                      <Title>bye bye- { fromUser }</Title>
                      <Description>I love redwine</Description>
                       <PicUrl>{Constants.REDWINE_PIC}</PicUrl>
                      <Url>{Constants.SHOP_AT_DIANPING}</Url>
                    </item>

        val items = Seq(item1)
        val node=WechatUtils.getNewsMsg(appUserId, fromUser, items)
        removeUser(fromUser)
        node
      }
      case Constants.EVT_TYP_SCAN => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_LOCATION => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_CLICK => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_VIEW => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case _ => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
    }

  }
}