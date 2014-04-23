package org.dragonstudio.wechat.util

import org.json4s._
import java.util._
import java.io._

import scala.xml.XML
import org.json4s.jackson.JsonMethods._
object WechatUtils {
  implicit val formats = DefaultFormats
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
      val menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
      val menu = loadMenuItems
      println(" Will post to " + menu_create_url)
      val message = HttpUtils.post(menu_create_url, menu);
      val json = parse(message)
      val errcode = (json \\ "errcode").extract[Int]
      val errmsg = (json \\ "errmsg").extract[String]

      println(" errcode -> " + errcode)

      println(" create menu -> " + menu)

      val responseMsg = "URL -> " + menu_create_url + " </br></br>  Menu -> " + menu 

      if (errcode != 0) {
        responseMsg + " </br></br> Failed to create menu due to " + errmsg
      } else {
        responseMsg
      }

    } catch {
      case e: Exception => e.printStackTrace(); "";
    }
  }

  def getTextMsg(fromUser: String, toUser: String, content: String): scala.xml.Elem = {
    val now = new Date().getTime()
    val message =
      <xml>
        <ToUserName>{ toUser }</ToUserName>
        <FromUserName>{ fromUser }</FromUserName>
        <Content>{ fromUser }, { content }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>
    message

  }

  def getNewsMsg(fromUser: String, toUser: String, content: String): scala.xml.Elem = {
    val now = new Date().getTime()
    val message =
      <xml>
        <ToUserName>{ toUser }</ToUserName>
        <FromUserName>{ fromUser }</FromUserName>
        <Content>{ fromUser }, { content }</Content>
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
}