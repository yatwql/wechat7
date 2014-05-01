package wechat7

import wechat7.controller._
import wechat7.controller.MenuController
import wechat7.controller.ChatRoomController
import wechat7.controller.SlickController
import wechat7.controller.WechatController
import wechat7.controller.StubController



class MyController extends WechatAppStack with ChatRoomController with SlickController with MenuController with WechatController with StubController{
get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }
  
}