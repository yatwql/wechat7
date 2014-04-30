package wechat7.routing
import wechat7.util._
class TextRouter extends Router {
  override def response(fromUser:String,appUserId:String,msgType:String,requestContent:String):String = {
    requestContent match{
       case "news" =>  WechatUtils.getNewsMsg(appUserId,fromUser,"PK news")
       case "news1" =>  { 
         val item1 =  <item>
            <Title>Here is news 1</Title>
            <Description>I love redwine</Description>
            <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
            <Url>http://www.dianping.com/shop/17180808/photos</Url>
          </item>
           
            val item2 =  <item>
            <Title>Here is news 2</Title>
            <Description>I love redwine</Description>
            <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
            <Url>http://www.dianping.com/shop/17180808/photos</Url>
          </item>
           
              val items=Seq(item1,item2)
         WechatUtils.getNewsMsg(appUserId,fromUser,"PK news",items) 
         }
       case _  =>  {
        val responseContent= " Thanks for your information '" + requestContent + "' with msg type " + msgType
         WechatUtils.getTextMsg(appUserId,fromUser,responseContent)
       }
     } 
  }
}