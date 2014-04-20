package org.dragonstudio.wechat.util
import org.json4s._

import scala.xml.XML
import org.json4s.jackson.JsonMethods._
object WechatUtils {
  
 def checkSignature(params:org.scalatra.Params): String =
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

      val demoJson = parse(message)

      println(message)
      val accessToken = compact(render(demoJson \\ "access_token"))
      println("accessToken "+accessToken)
      accessToken
    } catch {
      case e: Exception => e.printStackTrace(); "";
    }

  }
 
 def createMenu(){
   try {
      val access_token = WechatUtils.getAccess_token
      println(" access_token -> " + access_token)
      val menu_create = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
      val menu = Constants.jsonMenu
      println(" will post to "+menu_create)
    //  HttpUtils.post(menu_create, menu);

    //  println(" create menu -> " + menu)
      
      menu_create

    } catch {
      case e: Exception => e.printStackTrace();
    }
 }
}