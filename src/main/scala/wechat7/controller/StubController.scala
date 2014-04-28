package wechat7.controller

import wechat7.WechatAppStack
import wechat7.persistent.PageDao
trait StubController extends WechatAppStack {
 get("/pages/:slug") {
    contentType = "text/html"
    PageDao.pages find (_.slug == params("slug")) match {
      case Some(page) => ssp("/pages/show", "page" -> page)
      case None => halt(404, "Can not locate the page - " + params("slug"))
    }
  }
}