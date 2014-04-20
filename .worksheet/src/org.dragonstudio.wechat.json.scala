package org.dragonstudio.wechat
import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._
object json {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(167); 
  println("Welcome to the Scala worksheet");$skip(35); 
  val appId = "wx97c2cba93843a8e6";System.out.println("""appId  : String = """ + $show(appId ));$skip(53); 
  val appSecret = "4f488c849ddf23c83ef97e5b8482b8a1";System.out.println("""appSecret  : String = """ + $show(appSecret ));$skip(254); 
  val lotto = """
{
  "lotto":{
    "lotto-id":5,
    "winning-numbers":[2,45,34,23,7,5,3],
    "winners":[ {
      "winner-id":23,
      "numbers":[2,45,34,23,3, 5]
    },{
      "winner-id" : 54 ,
      "numbers":[ 52,3, 12,11,18,22 ]
    }]
  }
}
""";System.out.println("""lotto  : String = """ + $show(lotto ));$skip(1068); 

def getAccess_token(): String = { // 获得ACCESS_TOKEN

    val url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;

    val urlGet = new URL(url);
    val http = urlGet.openConnection().asInstanceOf[HttpURLConnection]

    http.setRequestMethod("GET"); //必须是get方式请求
    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    http.setDoOutput(true);
    http.setDoInput(true);
    System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); //连接超时30秒
    System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

    http.connect();

    val is = http.getInputStream();
    val size = is.available();
    val jsonBytes = new Array[Byte](size);
    is.read(jsonBytes);
    val message = new String(jsonBytes, "UTF-8");
    //  org.json4s.JsonInput
    val demoJson = parse(message)
    val access_tokenJson = demoJson \\ "access_token"

    println(message)
    val accessToken = compact(render(access_tokenJson))
    //demoJson
    accessToken

  };System.out.println("""getAccess_token: ()String""");$skip(27); 

  val json = parse(lotto);System.out.println("""json  : org.json4s.JValue = """ + $show(json ));$skip(35); 

  val jValue = json \\ "lotto-id";System.out.println("""jValue  : org.json4s.JValue = """ + $show(jValue ));$skip(26); val res$0 = 
  compact(render(jValue));System.out.println("""res0: String = """ + $show(res$0));$skip(228); 

  //getAccess_token

  val a = """  {"access_token":"DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mGN3x_L5KDLP8Waj5GH1VtOXVSnEMCYm2BVifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuNOKkk3jjlUx6555rvcRSss7miGdUWxchKUw","expires_in":7200}""";System.out.println("""a  : String = """ + $show(a ));$skip(38); 

  val b = parse(a) \\ "access_token";System.out.println("""b  : org.json4s.JValue = """ + $show(b ));$skip(30); 

  val c = compact(render(b));System.out.println("""c  : String = """ + $show(c ));$skip(60); val res$1 = 
                                                 c.getClass;System.out.println("""res1: Class[?0] = """ + $show(res$1));$skip(65); 
                                                  

  println(c);$skip(123); 
                                                  
                                                  val d=getAccess_token;System.out.println("""d  : String = """ + $show(d ));$skip(61); val res$2 = 
                                                  d.getClass;System.out.println("""res2: Class[?0] = """ + $show(res$2))}

  

}
