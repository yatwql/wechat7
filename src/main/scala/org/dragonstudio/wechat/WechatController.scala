package org.dragonstudio.wechat

import java.util.Date
import scala.xml.Elem
import java.net._
import org.json4s._

import org.dragonstudio.wechat.util.WechatUtils

import scala.xml.XML
import org.json4s.jackson.JsonMethods._
import org.dragonstudio.wechat.util._

class WechatController extends WechatAppStack with ChatRoomController {

  get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }

  get("/wechatauth") {
    contentType = "text/html"
    val result = WechatUtils.checkSignature(params)
    println(result)

  }

  post("/wechatauth") {

    println("request body is -> " + request.body)

    val requestXml = XML.loadString(request.body)
    wechatRouter(Some(requestXml))
  }

  get("/test") {
    val requestXml = <xml>
                       <ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
                       <Content><![CDATA[To test the function 您好]]></Content>
                       <FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
                       <MsgType><![CDATA[text]]></MsgType>
                     </xml>
    wechatRouter(Some(requestXml))
  }

  get("/createmenu") {
    contentType = "text/html"
    println("This is route for create menu")
    WechatUtils.createMenu()

  }

  def wechatRouter(requestXml: Option[Elem]) {
    contentType = "xml;charset=utf-8"

    val toUser = (requestXml.get \ "ToUserName").text
    val fromUser = (requestXml.get \ "FromUserName").text
    val content = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text

    val responseContent = " Thanks for your information '" + content + "' with msg type " + msgType

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
    write(message.toString())
  }
  

  def write(content:String) {
    val writer = response.getWriter()
    try {
      println(" response.contentType -> "+response.getContentType())
      println(" Writer content -> "+content)
      writer.write(content.toString())
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

}

