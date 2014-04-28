import wechat7._


import org.slf4j.LoggerFactory

import org.scalatra._
import javax.servlet.ServletContext


class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    val logger = LoggerFactory.getLogger(getClass)
    context.mount(new WechatController, "/*")
    context.addServlet("/*",classOf[wechat7.WechatController] )
  }
}
