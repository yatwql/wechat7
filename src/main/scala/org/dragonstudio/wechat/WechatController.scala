package org.dragonstudio.wechat

import java.util.Date

import scala.xml.XML

class WechatController extends WechatAppStack with ChatRoomController {
  val TOKEN = "WANGQL"

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
        <Content><![CDATA[Hello,]]>, your content is { content }, { fromUser }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>

    println(" response is " + res)
    write(res.toString())

  }
  
  def write(content:String){
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







