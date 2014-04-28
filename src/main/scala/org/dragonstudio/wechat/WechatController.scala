package org.dragonstudio.wechat

import org.dragonstudio.wechat.controller._



class WechatController extends WechatAppStack with ChatRoomController with SlickController with MenuController with WechatResponseController{
get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }
  
}