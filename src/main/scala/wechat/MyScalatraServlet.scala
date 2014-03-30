package wechat

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends DragonStudioWebchatAppStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Dragon Studio Wechat Appe</a>.
      </body>
    </html>
  }
  
}
