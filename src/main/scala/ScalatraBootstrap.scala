import org.dragonstudio.wechat._
import org.scalatra._
import javax.servlet.ServletContext


class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new WechatController, "/*")
    context.addServlet("/*",classOf[org.dragonstudio.wechat.WechatController] )
  }
}
