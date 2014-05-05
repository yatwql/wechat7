package wechat7.controller
import wechat7.persistent._
import wechat7.WechatAppStack

trait SlickController extends WechatAppStack {
  val slick = new SlickRepo with AdminRepo with ArticleRepo with UserRepo with VoteRepo
  get("/tables/create/:slug") {
    contentType = "text/html"
    val slug = params("slug")
    val message = "Create tables - " + slick.createTable(slug)
    println(message)
    ssp("/pages/showMessage", "title" -> "Create tables", "message" -> message)

  }
  get("/tables/drop/:slug") {
    contentType = "text/html"
    val slug = params("slug")
    val message = "Drop tables - " + slick.dropTable(slug)
    println(message)
    ssp("/pages/showMessage", "title" -> "Drop tables", "message" -> message)
  }

  get("/tables/flush/:slug") {
    contentType = "text/html"
    val slug = params("slug")
    val dropmsg = "Drop tables - " + slick.dropTable(slug)
    val createmsg = "Create tables - " + slick.createTable(slug)
    val popmsg = "Populate tables - " + slick.populateTable(slug)
    val message = dropmsg + ";" + createmsg + ";" + popmsg
    println(message)
    ssp("/pages/showMessage", "title" -> "Flush tables", "message" -> message)
  }

  get("/audit/list") {
    contentType = "text/html"
  }

}