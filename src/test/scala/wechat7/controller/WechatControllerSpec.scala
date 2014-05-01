package wechat7.filesize

import org.scalatra.test.specs2._
import wechat7.MyController
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WechatControllerSpec  extends ScalatraSpec { def is =
  "GET /wechatauth on WechatController"                     ^
    "should return status 200"                  ! auth^
                                                end

  addServlet(classOf[MyController], "/*")

  def auth = get("/wechatauth?signature=439bde0d5af260f241a327b44eef7e531c20c02a&echostr=2821088496660249143&timestamp=1398698533&nonce=1773830803") {
    status must_== 200
  }
 
}