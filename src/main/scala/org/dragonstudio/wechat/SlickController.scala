package org.dragonstudio.wechat
import org.dragonstudio.wechat.persistent._

trait SlickController  extends WechatAppStack {
  val slick = new SlickUtils
  get("/tables/create") {
    contentType = "text/html"
      slick.createTables
      println("Create tables")
     "Create tables"
   
  }
  get("/tables/drop") {
    contentType = "text/html"
      slick.dropTables
      println("Drop tables")
    "Drop tables"
  }
  
    get("/tables/flush") {
    contentType = "text/html"
      slick.flush
      println("Flush tables")
    "Flush tables"
  }

}