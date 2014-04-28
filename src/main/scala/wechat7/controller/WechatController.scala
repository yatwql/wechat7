package wechat7.controller

import scala.xml.Elem
import scala.xml.XML

import wechat7.WechatAppStack
import wechat7.util.WechatUtils


trait WechatController extends WechatAppStack {
  get("/wechatauth") {
    contentType = "text/html"
    val result = WechatUtils.checkSignature(params)
    println(result)
    result
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

  

  def wechatRouter(requestXml: Option[Elem]) {
    contentType = "xml;charset=utf-8"
    this.write(WechatUtils.getResponse(requestXml))
  }
  

}

