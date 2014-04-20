package org.dragonstudio.wechat.util
import org.json4s._

import scala.xml.XML
import org.json4s.jackson.JsonMethods._
object WechatUtils {

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
    implicit val formats = DefaultFormats
    try {
      val message = HttpUtils.get(url)

      val demoJson = parse(message)

      println("The return message -> " + message)
      //val accessToken = compact(render(demoJson \\ "access_token"))

      val accessToken = (demoJson \ "access_token").extract[String]
      println("The accessToken -> " + accessToken)
      accessToken
    } catch {
      case e: Exception => e.printStackTrace(); "";
    }

  }

  def createMenu() :String ={
    try {
      val access_token = WechatUtils.getAccess_token
      println(" access_token -> " + access_token)
      val menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
      val menu = Constants.jsonMenu
      println(" Will post to " + menu_create_url)
     // HttpUtils.post(menu_create_url, menu);

      println(" create menu -> " + menu)

      "URL -> "+menu_create_url+", Menu -> "+menu

    } catch {
      case e: Exception => e.printStackTrace();"";
    }
  }
}