package wechat7.controller
import wechat7.persistent._
import wechat7.WechatAppStack

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