package org.dragonstudio.wechat

import java.util.Date
import java.net._

import scala.xml.XML

class WechatController extends WechatAppStack with ChatRoomController {
  val TOKEN = "WANGQL"

  val MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TOKEN;

  get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }

  get("/wechatauth") {
    contentType = "text/html"
    val result = checkSignature()
    println(result)

  }

  post("/wechatauth") {
    contentType = "xml"
    println("request body is -> " + request.body)

    val wxl = XML.loadString(request.body)

    val toUser = (wxl \ "ToUserName").text
    val fromUser = (wxl \ "FromUserName").text
    val content = (wxl \ "Content").text
    val msgType = (wxl \ "MsgType").text

    val now = new Date().getTime()
    val res =
      <xml>
        <ToUserName>{ fromUser }</ToUserName>
        <FromUserName>{ toUser }</FromUserName>
        <Content>{ fromUser }, your content is { content } </Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>

    println(" response is " + res)
    write(res.toString())

  }

  post("/createmenu") {
    val jsonMenu = "{\"button\":[{\"name\":\"生活助手\",\"sub_button\":[{\"key\":\"11\",\"name\":\"天气预报\",\"type\":\"click\"},{\"key\":\"12\",\"name\":\"公交查询\",\"type\":\"click\"}]},{\"name\":\"音智达\",\"sub_button\":[{\"key\":\"21\",\"name\":\"好东西哦\",\"type\":\"click\"},{\"key\":\"22\",\"name\":\"人脸识别\",\"type\":\"click\"}]},{\"name\":\"更多体验\",\"sub_button\":[{\"key\":\"33\",\"name\":\"幽默笑话\",\"type\":\"click\"},{\"name\":\"View类型的\",\"type\":\"view\",\"url\":\"http://m.baidu.com\"}]}]}";
    //  post(MENU_CREATE, jsonMenu)
    try {
      val url = new URL(MENU_CREATE);
      val conn = url.openConnection()
      val http: HttpURLConnection = conn.asInstanceOf[HttpURLConnection]

      http.setRequestMethod("POST");
      http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      http.setDoOutput(true);
      http.setDoInput(true);
      System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); //连接超时30秒
      System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
      http.connect();
      val os = http.getOutputStream();
      os.write(jsonMenu.getBytes("UTF-8")); //传入参数    
      os.flush();
      os.close();

      val is = http.getInputStream();
      val size = is.available();
      val jsonBytes = new Array[Byte](size)
      is.read(jsonBytes);
      val message = new String(jsonBytes, "UTF-8");
      println(" create menu -> "+jsonMenu)

    } catch {
      case e: Exception => e.printStackTrace();
    }
  }

  def write(content: String) {
    val writer = response.getWriter()
    try {
      writer.write(content)
    } catch {
      case e: Exception =>
    } finally {
      writer.close()
    }
  }

  error {
    case t: Throwable => t.printStackTrace()
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

  def checkSignature(): String =
    {
      val signature = params.getOrElse("signature", "")
      println("signature = " + signature)
      val timestamp = params.getOrElse("timestamp", "")
      println("timestamp = " + timestamp)
      val nonce = params.getOrElse("nonce", "")
      println("nonce = " + nonce)
      val echostr = params.getOrElse("echostr", "")
      println("echostr = " + echostr)

      val token = TOKEN
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

}







