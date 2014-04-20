package org.dragonstudio.wechat

import java.util.Date
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
    contentType = "xml;charset=utf-8"
    println("request body is -> " + request.body)

    val requestXml = XML.loadString(request.body)

    val toUser = (requestXml \ "ToUserName").text
    val fromUser = (requestXml \ "FromUserName").text
    val content = (requestXml \ "Content").text
    val msgType = (requestXml \ "MsgType").text

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
    println(" response message class is " + message.getClass().getName())
    println(" response message is " + message.toString())
    val now = new Date().getTime()
    val message1 =
      <xml>
        <ToUserName>{ fromUser }</ToUserName>
        <FromUserName>{ toUser }</FromUserName>
        <Content>{ fromUser }, { content },message type { msgType}</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[text]]></MsgType>
        <FuncFlag>0</FuncFlag>
      </xml>
        
        println(" response message 1 class  is " + message1.getClass().getName())
        println(" response message  1 is " + message1.toString())
        
     
   
   // write(message.toString())
    
    val message2 =
      <xml>
        <ToUserName>{ fromUser }</ToUserName>
        <FromUserName>{ toUser }</FromUserName>
        <Content>{ fromUser }, { content }</Content>
        <CreateTime>{ now }</CreateTime>
        <MsgType><![CDATA[news]]></MsgType>
        <ArticleCount>1</ArticleCount>
        <Articles>
          <item>
            <Title>Here is news</Title>
            <Description>I am description</Description>
            <PicUrl>http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg</PicUrl>
            <Url>http://www.iteye.com</Url>
          </item>
        </Articles>
        <FuncFlag>0</FuncFlag>
      </xml>
        
        println(" response message 2 class  is " + message2.getClass().getName())
        println(" response message  2 is " + message2.toString())
    write(message2.toString())

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

