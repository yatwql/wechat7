package wechat7.controller

import wechat7.WechatAppStack
import wechat7.repo._
import wechat7.util._
import javax.servlet.annotation.MultipartConfig
import org.scalatra.servlet.FileUploadSupport
import org.scalatra.ScalatraServlet
import org.scalatra.servlet.FileItem

trait ArticleController extends WechatAppStack with FileUploadSupport {
  self: ScalatraServlet =>
  class ActionRepoImpl extends ActionRepo {

  }
  val repo = new ActionRepoImpl

  get("/wechat/articles") {
    val list = repo.getArticleList(20)
    ssp("/wechat/articles", "title" -> "List articles", "list" -> list)
  }

  get("/wechat/article/:slug") {
    val slug = params("slug")
    val article = repo.getArticle(slug.toInt)
    ssp("/wechat/article/view", "title" -> "Show article", "article" -> article)
  }

}