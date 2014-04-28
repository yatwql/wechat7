package wechat7

import wechat7.controller._



class WechatController extends WechatAppStack with ChatRoomController with SlickController with MenuController with WechatResponseController{
get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }
  
}