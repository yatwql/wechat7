package org.dragonstudio.wechat

import java.util.Date
import java.net._
import org.json4s._

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
    contentType = "xml"
    println("request body is -> " + request.body)

    val wxl = XML.loadString(request.body)

    val toUser = (wxl \ "ToUserName").text
    val fromUser = (wxl \ "FromUserName").text
    val content = (wxl \ "Content").text
    val msgType = (wxl \ "MsgType").text

    val now = new Date().getTime()

    val responseContent = " your content is " + content + " , your msg type is " + msgType

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
    println(" Message Type is " + msgType)
    println(" response message is " + message)
    write(message.toString())

  }

  get("/createmenu") {
    contentType = "text/html"
    println("This is route for create menu")
    WechatUtils.createMenu()

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

}

