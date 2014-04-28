package wechat7.util

import java.io.InputStream
import wechat7.persistent._
import java.util.Date

import scala.xml.Elem

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jvalue2extractable
import org.json4s.jvalue2monadic
import org.json4s.string2JsonInput
object WechatUtils {
  implicit val formats = DefaultFormats
   val slick = new SlickUtils
  def checkSignature(params: org.scalatra.Params): String =
    {
      val signature = params.getOrElse("signature", "")
      println("signature = " + signature)
      val timestamp = params.getOrElse("timestamp", "")
      println("timestamp = " + timestamp)
      val nonce = params.getOrElse("nonce", "")
      println("nonce = " + nonce)
      val echostr = params.getOrElse("echostr", "")
      println("echostr = " + echostr)

      val token = Constants.TOKEN
      println("token = " + token)
      val tmpStr = Array(token, timestamp, nonce).sortWith(_ < _).mkString

      println("tmpStr = " + tmpStr)

      val md = java.security.MessageDigest.getInstance("SHA1")

      val ha = md.digest(tmpStr.getBytes("UTF-8")).map("%02x".format(_)).mkString

      println("ha = " + ha)

      println("signature = " + signature)

      if (ha == signature) {
        echostr
      } else {
        "InvalidSigatureResult"
      }

    }

  def getAccess_token(): String = { // 获得ACCESS_TOKEN

    val url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.appId + "&secret=" + Constants.appSecret;

    try {
      val message = HttpUtils.get(url)

      val json = parse(message)

      println("The return message -> " + message)
      //val accessToken = compact(render(json \\ "access_token"))

      val accessToken = (json \ "access_token").extract[String]
      println("The accessToken -> " + accessToken)
      accessToken
    } catch {
      case e: Exception => e.printStackTrace(); "";
    }

  }

  def loadMenuItems: String = {
    val in: InputStream = getClass().getResourceAsStream("/menu.json")
    val size = in.available();
    val data = new Array[Byte](size)
    in.read(data);
    val message = new String(data, "UTF-8");
    message
  }

  def createMenu(): String = {
    try {
      val access_token = WechatUtils.getAccess_token
      //val access_token = "Testing"
      println(" access_token -> " + access_token)
      val menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
      val menu = loadMenuItems
      println(" Will post to " + menu_url)
      val message = HttpUtils.post(menu_url, menu);
      val json = parse(message)
      val errcode = (json \\ "errcode").extract[Int]
      val errmsg = (json \\ "errmsg").extract[String]

      println(" errcode -> " + errcode)

      println(" create menu -> " + menu)

      val responseMsg = "URL -> " + menu_url + " </br></br>  Menu -> " + menu 

      if (errcode != 0) {
        responseMsg + " </br></br> Failed to create menu due to " + errmsg
      } else {
        responseMsg
      }

    } catch {
      case e: Exception => e.printStackTrace(); "";
    }
  }
  
  def getMenu(): String = {
    val menu_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="
    visitPageByToken("Get menu",menu_url)
  }
  
   def deleteMenu(): String = {
    val menu_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
    visitPageByToken("Delete menu",menu_url)
  }
  
  def visitPageByToken(action:String,url:String):String={
    try {
      val access_token = WechatUtils.getAccess_token
   
      println(" access_token -> " + access_token)
      val menu_url = url+ access_token;
      println(" menu ul -> "+menu_url)
      val message = HttpUtils.get(menu_url)

      println(action+" -> " + message)

      val responseMsg = "URL -> " + menu_url + " </br></br>  Menu -> " + message 

        responseMsg
      

    } catch {
      case e: Exception => e.printStackTrace(); "";
    }
  }

  def getTextMsg(fromUser: String, toUser: String, content: String): Elem = {
    val now = new Date().getTime()
    val message =
      <xml>
        <ToUserName>{ toUser }</ToUserName>
        <FromUserName>{ fromUser }</FromUserName>
        <Content>{ toUser }, { content }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>
    message

  }

  def getNewsMsg(fromUser: String, toUser: String, content: String): Elem = {
    val now = new Date().getTime()
    val message =
      <xml>
        <ToUserName>{ toUser }</ToUserName>
        <FromUserName>{ fromUser }</FromUserName>
        <Content>{ toUser }, { content }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[news]]></MsgType>
        <ArticleCount>1</ArticleCount>
        <Articles>
          <item>
            <Title>Here is news</Title>
            <Description>I love redwine</Description>
            <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
            <Url>http://www.dianping.com/shop/17180808/photos</Url>
          </item>
        </Articles>
        <FuncFlag>0</FuncFlag>
      </xml>
    message

  }
  
  def getResponse(requestXml: Option[Elem]) :String ={
    
    val toUser = (requestXml.get \ "ToUserName").text
    val fromUser = (requestXml.get \ "FromUserName").text
    val content = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text

    val responseContent = " Thanks for your information '" + content + "' with msg type " + msgType
   
   // slick.insert(Message(fromUser,toUser,msgType,content,new java.util.Date()))

    val message = msgType match {
      case Constants.REQ_MESSAGE_TYPE_TEXT => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_IMAGE => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_VOICE => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_VIDEO => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_LOCATION => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_LINK => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
      case Constants.REQ_MESSAGE_TYPE_EVENT => WechatUtils.getNewsMsg(toUser, fromUser, responseContent);
      case _ => WechatUtils.getTextMsg(toUser, fromUser, responseContent);
    }
    println("Get Message Type  " + msgType+" from user "+fromUser)
    message.toString()
  }
}