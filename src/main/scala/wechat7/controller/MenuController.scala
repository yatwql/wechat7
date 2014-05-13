package wechat7.controller

import wechat7.WechatAppStack
import wechat7.repo._
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
     ssp("/wechat/menu/upload")
  }

  post("/wechat/menu/update") {
    val item: FileItem = fileParams.get("document").getOrElse(halt(500))
    val arr =item.get()
    val menu =new String(arr)
    adminRepo.saveMenu(menu)
    ssp("/wechat/menu/update","item" ->item,"menu" -> menu)
   /*
    <html>
      <head>
        <title>Upload menu file successful.</title>
      </head>
      <body>
        <p>File Name: { item.name }</p>
        <p>content type: { item.contentType.getOrElse("unknown") }</p>
        <p>File Size: { item.size }</p>
        <p>Saved content: { menu}</p>
        <p><a href="/wechat/menu/create" >Create the menu</a></p>
      </body>
    </html>
*/
  }

}