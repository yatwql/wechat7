package wechat7.controller
import wechat7.persistent._
import wechat7.WechatAppStack

trait SlickController extends WechatAppStack {
  val slick = new SlickRepo
  get("/tables/create") {
    //contentType = "text/html"
    slick.createTables
    println("Create tables")
    ssp("/pages/showMessage", "title" -> "Create tables", "message" -> "Create tables")

  }
  get("/tables/drop") {
    contentType = "text/html"
    slick.dropTables
    println("Drop tables")
    ssp("/pages/showMessage", "title" -> "Drop tables", "message" -> "Drop tables")
  }

  get("/tables/flush") {
    contentType = "text/html"
    slick.flush
    println("Flush tables")
    ssp("/pages/showMessage", "title" -> "Flush tables", "message" -> "Flush tables")
  }

}