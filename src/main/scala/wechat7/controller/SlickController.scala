package wechat7.controller
import wechat7.repo._
import wechat7.WechatAppStack

trait SlickController extends WechatAppStack {
  val slick = new SlickRepo with AdminRepo with ArticleRepo with UserRepo with VoteRepo
  get("/tables/:action/:slug") {
    contentType = "text/html"
    val slug = params("slug")
    val action= params("action")
    val message= slick.doAction(slug, action)
    val title =" Action -> "+action
    println(message)
    ssp("/pages/showMessage", "title" -> title, "message" -> message)
  }

  get("/audit/list") {
    contentType = "text/html"
  }

}