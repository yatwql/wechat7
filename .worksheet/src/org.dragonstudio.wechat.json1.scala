package wechat7

import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._

object json1 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(170); 
  println("Welcome to the Scala worksheet");$skip(35); 
  val appId = "wx97c2cba93843a8e6";System.out.println("""appId  : String = """ + $show(appId ));$skip(53); 
  val appSecret = "4f488c849ddf23c83ef97e5b8482b8a1";System.out.println("""appSecret  : String = """ + $show(appSecret ));$skip(1020); 
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
  };System.out.println("""getAccess_token: ()String""");$skip(29); 
  
  val abc=getAccess_token;System.out.println("""abc  : String = """ + $show(abc ));$skip(105); val res$0 = 
                                                  
                                                  abc;System.out.println("""res0: String = """ + $show(res$0));$skip(63); 
                                                  println(abc)}
}
