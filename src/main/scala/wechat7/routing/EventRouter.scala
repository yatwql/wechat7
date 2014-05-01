package wechat7.routing
import wechat7.util._
import scala.xml._
class EventRouter extends Router {
  override def responseImpl(fromUser: String, appUserId: String, msgType: String,requestXml:  Option[Elem], requestContent: String): Node = {
   
    val event= (requestXml.get \ "Event").text
     val responseContent = " Thanks for your information '" + requestContent + "' this is news for event "+event
     
     event match {
      case "subscribe" => {
         val item1 = <item>
                      <Title>Welcome you to subscript my account - {fromUser}</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                      <Url>http://www.dianping.com/shop/17180808/photos</Url>
                    </item>


        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, fromUser, "subscript news", items)
       
        }
      case "unsubscribe" => {
         val item1 = <item>
                      <Title>It's a pity that you leave me alone- {fromUser}</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                      <Url>http://www.dianping.com/shop/17180808/photos</Url>
                    </item>


        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, fromUser, "subscript news", items)
       
        }
      case "SCAN" =>WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
      case "LOCATION" =>WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
      case "CLICK" =>WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
      case "VIEW" =>WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
      case _ =>WechatUtils.getNewsMsg(appUserId, fromUser, responseContent);
    }
    
  }
}