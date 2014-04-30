package wechat7.controller

import scala.xml.Elem
import wechat7.routing._
import scala.xml.XML

import wechat7.WechatAppStack
import wechat7.util.WechatUtils


trait WechatController extends WechatAppStack with SlickController {
  get("/wechatauth") {
    contentType = "text/html"
    val result = WechatUtils.checkSignature(params)
    println(result)
    result
  }
  
  get("/wechat"){
    redirect("/wechatauth")
  }

  post("/wechatauth") {
    println("request body is -> " + request.body)
    val requestXml = XML.loadString(request.body)
    wechatRouter(Some(requestXml))
  }
  
   post("/wechat"){
    redirect("/wechatauth")
  }
   
  get("/test/text/:slug") {
    val slug =params("slug")
   
    val requestXml = <xml>
                       <ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
                       <Content>{slug}</Content>
                       <FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
                       <MsgType><![CDATA[text]]></MsgType>
                     </xml>
    contentType = "xml;charset=utf-8"
    wechatRouter(Some(requestXml))
  }
  
    get("/test/news/:slug") {
    val slug =params("slug")
   
    val requestXml = <xml>
                       <ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
                       <Content>news{slug}</Content>
                       <FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
                       <MsgType><![CDATA[text]]></MsgType>
                     </xml>
    contentType = "xml;charset=utf-8"
    wechatRouter(Some(requestXml))
  }

  def wechatRouter(requestXml: Option[Elem]) {
    contentType = "xml;charset=utf-8"
    // ssp("/wechat/text","layout" -> "","fromUser" -> "123","toUser" -> "abc", "content"->"ccc", "now" -> 2222)
     
    write(Rounter.response(requestXml))
  }
  

}

