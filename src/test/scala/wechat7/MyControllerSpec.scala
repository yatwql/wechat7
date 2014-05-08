package wechat7

import org.scalatra.test.specs2._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
//import org.specs.runner.JUnitSuiteRunner
//@RunWith(classOf[JUnitRunner])

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
@RunWith(classOf[JUnitRunner])
class MyControllerSpec extends ScalatraSpec { def is =
  "GET / on MyScalatraServlet"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[MyController], "/*")

  def root200 = get("/") {
    status must_== 200
  }

}
