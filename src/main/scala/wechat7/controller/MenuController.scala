package wechat7.controller

import wechat7.WechatAppStack
import wechat7.util._
import javax.servlet.annotation.MultipartConfig
import org.scalatra.servlet.FileUploadSupport
import org.scalatra.ScalatraServlet
import org.scalatra.servlet.FileItem

@MultipartConfig(maxFileSize = 10240, location = "src/main/resources/")
trait MenuController extends WechatAppStack with FileUploadSupport {
  self: ScalatraServlet =>

  get("/wechat/menu/create") {
    contentType = "text/html"
    println("This is route for create menu")
    val responseMsg = WechatUtils.createMenu()
    responseMsg
  }

  get("/wechat/menu/get") {
    contentType = "text/html"
    println("This is route for get menu")
    val responseMsg = WechatUtils.getMenu()
    responseMsg
  }

  get("/wechat/menu/delete") {
    contentType = "text/html"
    println("This is route for delete menu")
    val responseMsg = WechatUtils.getMenu()
    responseMsg
  }

  get("/wechat/menu/upload") {
    contentType = "text/html"
    <html>
      <head>
        <title>Upload a document</title>
      </head>
      <body>
        <form action="/wechat/menu/update" enctype="multipart/form-data" method="post">
          <input type="file" name="document"/>
          <input type="submit"/>
        </form>
      </body>
    </html>
  }

  post("/wechat/menu/update") {
    val item: FileItem = fileParams.get("document").getOrElse(halt(500))
    val mediaBase = "./media"
    val uuid = java.util.UUID.randomUUID()
    val tm = "menu22.json"
    val targetFile = "%s/%s".format(mediaBase, tm)
    item.write(targetFile)
    <html>
      <head>
        <title>Upload successful.</title>
      </head>
      <body>
        <p>Name: { item.name }</p>
        <p>content type: { item.contentType.getOrElse("unknown") }</p>
        <p>Size: { item.size }</p>
        <p>Saved to: { targetFile }</p>
      </body>
    </html>

  }

}