package org.dragonstudio.wechat

import java.util.Date
import scala.xml.Elem
import java.net._
import org.json4s._

import org.dragonstudio.wechat.util.WechatUtils

import scala.xml.XML
import org.json4s.jackson.JsonMethods._
import org.dragonstudio.wechat.util._

class WechatController extends WechatAppStack with ChatRoomController with SlickController {

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
    contentType = "xml;charset=utf-8"
    wechatRouter(Some(requestXml))
  }

  get("/createmenu") {
    contentType = "text/html"
    println("This is route for create menu")
    WechatUtils.createMenu()

  }

  def wechatRouter(requestXml: Option[Elem]) {
    contentType = "xml;charset=utf-8"
    write(WechatUtils.getResponse(requestXml))
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

