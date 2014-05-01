package wechat7.routing
import wechat7.util._
import scala.xml._
class TextRouter extends Router {
  override def responseImpl(fromUser: String, appUserId: String, msgType: String, requestXml:  Option[Elem],requestContent: String): Node = {
    val nickname =getNickname(fromUser)
    val Pattern = "(\\d+)".r
    requestContent match {
      case "news" => {
        val item1 = <item>
                      <Title>news for { nickname }</Title>
                      <Description>I love redwine</Description>
                       <PicUrl>{Constants.REDWINE_PIC}</PicUrl>
                      <Url>{Constants.SHOP_AT_DIANPING}</Url>
                    </item>


        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, fromUser, items)
      }
      case Pattern(s) => {
        val item = <item>
                     <Title>Here is news till {s} </Title>
                     <Description>I love redwine,{ nickname }</Description>
                     <PicUrl>{Constants.REDWINE_PIC}</PicUrl>
                      <Url>{Constants.SHOP_AT_DIANPING}</Url>
                   </item>
        val items = Seq(item,item,item)
       
        WechatUtils.getNewsMsg(appUserId, fromUser,  items)
      }

      case _ => {
        val responseContent = nickname+" say '" + requestContent + "'  " 
        WechatUtils.getTextMsg(appUserId, fromUser, responseContent)
      }
    }
  }
}