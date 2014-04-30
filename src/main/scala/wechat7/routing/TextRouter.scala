package wechat7.routing
import wechat7.util._
class TextRouter extends Router {
  override def responseImpl(fromUser: String, appUserId: String, msgType: String, requestXmlContent:String,requestContent: String): String = {
    val Pattern = "(\\d+)".r
    requestContent match {
      case "news" => {
        val item1 = <item>
                      <Title>Here is news 1</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                      <Url>http://www.dianping.com/shop/17180808/photos</Url>
                    </item>


        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, fromUser, "PK news", items)
      }
      case Pattern(s) => {
        val item = <item>
                     <Title>Here is news till {s} </Title>
                     <Description>I love redwine</Description>
                     <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
                     <Url>http://www.dianping.com/shop/17180808/photos</Url>
                   </item>
        val items = Seq(item,item,item)
       
        WechatUtils.getNewsMsg(appUserId, fromUser, "PK news "+s, items)
      }

      case _ => {
        val responseContent = " Thanks for your information '" + requestContent + "' with msg type " + msgType
        WechatUtils.getTextMsg(appUserId, fromUser, responseContent)
      }
    }
  }
}