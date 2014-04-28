package wechat7.controller

import wechat7.WechatAppStack
import wechat7.util.WechatUtils

trait MenuController  extends WechatAppStack{
  
  get("/menu/create") {
    contentType = "text/html"
    println("This is route for create menu")
    WechatUtils.createMenu()

  }

}