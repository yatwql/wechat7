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
        val item1 = <item>
                      <Title>Welcome you - { fromUser }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                      <Url>http://www.dianping.com/shop/17180808/photos</Url>
                    </item>

        val items = Seq(item1)
        //users.insert(User(openId, nickname, sex, city,country,province,language,headimgurl,subscriptTime,locationX,locationY))
        val node = WechatUtils.getNewsMsg(appUserId, fromUser, items)
        addUser(WechatUtils.getUserInfo(fromUser))
        node
      }
      case Constants.EVT_TYP_UNSUBSCRIBE => {
        val item1 = <item>
                      <Title>bye bye- { fromUser }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                      <Url>http://www.dianping.com/shop/17180808/photos</Url>
                    </item>

        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, fromUser, items)

      }
      case Constants.EVT_TYP_SCAN => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_LOCATION => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_CLICK => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case Constants.EVT_TYP_VIEW => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
      case _ => WechatUtils.getNewsMsg(appUserId, fromUser, "Hello world ", responseContent, Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING);
    }

  }
}