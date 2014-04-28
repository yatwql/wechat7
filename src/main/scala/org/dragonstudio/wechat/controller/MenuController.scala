package org.dragonstudio.wechat.controller

import org.dragonstudio.wechat.WechatAppStack
import org.dragonstudio.wechat.util.WechatUtils

trait MenuController  extends WechatAppStack{
  
  get("/menu/create") {
    contentType = "text/html"
    println("This is route for create menu")
    WechatUtils.createMenu()

  }

}