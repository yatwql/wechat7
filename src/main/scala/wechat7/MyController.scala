package wechat7

import wechat7.controller._



class MyController extends WechatAppStack with ChatRoomController with SlickController with MenuController with WechatController with StubController{
get("/") {
    contentType = "text/html"
    ssp("/pages/index","layout" -> "")
  }
  
}