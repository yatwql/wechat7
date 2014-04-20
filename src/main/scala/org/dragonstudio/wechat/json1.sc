package org.dragonstudio.wechat

import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._

object json1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val appId = "wx97c2cba93843a8e6"                //> appId  : String = wx97c2cba93843a8e6
  val appSecret = "4f488c849ddf23c83ef97e5b8482b8a1"
                                                  //> appSecret  : String = 4f488c849ddf23c83ef97e5b8482b8a1
  def getAccess_token(): String = {
    val url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret

    val urlGet = new URL(url)
    val http = urlGet.openConnection().asInstanceOf[HttpURLConnection]

    http.setRequestMethod("GET") //必须是get方式请求
    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    http.setDoOutput(true)
    http.setDoInput(true)
    System.setProperty("sun.net.client.defaultConnectTimeout", "30000") //连接超时30秒
    System.setProperty("sun.net.client.defaultReadTimeout", "30000") //读取超时30秒

    http.connect()

    val is = http.getInputStream()
    val size = is.available()
    val jsonBytes = new Array[Byte](size)
    is.read(jsonBytes)
    val message = new String(jsonBytes, "UTF-8")
    //  org.json4s.JsonInput
    val demoJson = parse(message)
    val access_tokenJson = demoJson \\ "access_token"

    println(message)
    val accessToken = compact(render(access_tokenJson))
    accessToken
  }                                               //> getAccess_token: ()String
  
  val abc=getAccess_token                         //> {"access_token":"WfDfJhSsD0Bif8aa4myCqPEwRRGcjqd0RbexHWesW3QAfT8trBn0oAAQVa
                                                  //| hCbYgEZquimkVhwChf06-QNLKiJwqLXfFZwjz4nUB_TQwJjnpbYcEH2WIq4BcDAE-w6Z3U2bZmS
                                                  //| INrZ5P9_DBVOBBtOg","expires_in":7200}
                                                  //| abc  : String = "WfDfJhSsD0Bif8aa4myCqPEwRRGcjqd0RbexHWesW3QAfT8trBn0oAAQVa
                                                  //| hCbYgEZquimkVhwChf06-QNLKiJwqLXfFZwjz4nUB_TQwJjnpbYcEH2WIq4BcDAE-w6Z3U2bZmS
                                                  //| INrZ5P9_DBVOBBtOg"
                                                  
                                                  abc
                                                  //> res0: String = "WfDfJhSsD0Bif8aa4myCqPEwRRGcjqd0RbexHWesW3QAfT8trBn0oAAQVah
                                                  //| CbYgEZquimkVhwChf06-QNLKiJwqLXfFZwjz4nUB_TQwJjnpbYcEH2WIq4BcDAE-w6Z3U2bZmSI
                                                  //| NrZ5P9_DBVOBBtOg"
                                                  println(abc)
                                                  //> "WfDfJhSsD0Bif8aa4myCqPEwRRGcjqd0RbexHWesW3QAfT8trBn0oAAQVahCbYgEZquimkVhwC
                                                  //| hf06-QNLKiJwqLXfFZwjz4nUB_TQwJjnpbYcEH2WIq4BcDAE-w6Z3U2bZmSINrZ5P9_DBVOBBtO
                                                  //| g"
}