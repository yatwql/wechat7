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
    

    msgType match {
      case Constants.REQ_MESSAGE_TYPE_TEXT => println(" Here is text")
      case Constants.REQ_MESSAGE_TYPE_IMAGE => println(" Here is Image")
      case Constants.REQ_MESSAGE_TYPE_VOICE => println(" Here is voice")
      case Constants.REQ_MESSAGE_TYPE_VIDEO => println(" Here is video")
      case Constants.REQ_MESSAGE_TYPE_LOCATION => println(" Here is location")
      case Constants.REQ_MESSAGE_TYPE_LINK => println(" Here is link")
      case Constants.REQ_MESSAGE_TYPE_EVENT => println(" Here is event")
      case _ =>
    }
    
    val res =
      <xml>
        <ToUserName>{ fromUser }</ToUserName>
        <FromUserName>{ toUser }</FromUserName>
        <Content>{ fromUser }, your content is { content } , your msg type is {msgType }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>

    println(" response is " + res)
    write(res.toString())

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

