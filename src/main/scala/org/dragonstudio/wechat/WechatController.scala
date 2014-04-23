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
                       <Content><![CDATA[To test the function]]></Content>
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

  def wechatRouter(requestXml: Option[scala.xml.Elem]) {
    contentType = "xml;charset=utf-8"

    val toUser = (requestXml.get \ "ToUserName").text
    val fromUser = (requestXml.get \ "FromUserName").text
    val content = (requestXml.get \ "Content").text
    val msgType = (requestXml.get \ "MsgType").text

    val responseContent = " your content is '" + content + "' , your msg type is " + msgType

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
            <Description>I love redwine</Description>
            <PicUrl>http://www.cnyangjiu.com/html/UploadFiles/201051975110330.jpg</PicUrl>
            <Url>http://www.dianping.com/shop/17180808/photos</Url>
          </item>
        </Articles>
        <FuncFlag>0</FuncFlag>
      </xml>
//message2.text
    println(" response message 2 class  is " + message2.getClass().getName())
    println(" response message  2 is " + message2.toString)
    
    write(message)
  }

  def write (responseXml:Elem){
    write(responseXml.toString())
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
      response.flushBuffer()
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

