package wechat7.controller
import wechat7.persistent._
import wechat7.WechatAppStack

trait SlickController extends WechatAppStack {
  val slick = new SlickRepo
  get("/tables/create") {
    contentType = "text/html"
    val message =slick.createTables
    println(message)
    ssp("/pages/showMessage", "title" -> "Create tables", "message" -> message)

  }
  get("/tables/drop") {
    contentType = "text/html"
    val message=slick.dropTables
    println(message)
    ssp("/pages/showMessage", "title" -> "Drop tables", "message" -> message)
  }

  get("/tables/flush") {
    contentType = "text/html"
    val message=slick.flush
    println(message)
    ssp("/pages/showMessage", "title" -> "Flush tables", "message" -> message)
  }
  
  get("/audit/list"){
     contentType = "text/html"
  }

}