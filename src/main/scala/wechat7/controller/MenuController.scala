package wechat7.controller

import wechat7.WechatAppStack
import wechat7.persistent._
import wechat7.util._
import javax.servlet.annotation.MultipartConfig
import org.scalatra.servlet.FileUploadSupport
import org.scalatra.ScalatraServlet
import org.scalatra.servlet.FileItem

@MultipartConfig(maxFileSize = 10240, location = "src/main/resources/")
trait MenuController extends WechatAppStack with FileUploadSupport  {
  self: ScalatraServlet =>
  class AdminRepoImpl extends AdminRepo{
    
  }
  val adminRepo =new AdminRepoImpl
  get("/wechat/menu/create") {
    contentType = "text/html"
    println("This is route for create menu")
    val menuFromDB = adminRepo.loadLatestMenu
    val menu = menuFromDB match {
      case Some(menu1) => {
        println(" Use menu from db")
        menu1
      }
      case None => {
        println(" Use menu from file ")
        WechatUtils.loadMenuFromFile
      } 
    }
    val responseMsg = WechatUtils.createMenu(menu)
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
    val responseMsg = WechatUtils.deleteMenu()
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