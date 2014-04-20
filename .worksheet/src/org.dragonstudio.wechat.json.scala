package org.dragonstudio.wechat
import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._
import org.dragonstudio.wechat.util._
object json {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(205); 
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
""";System.out.println("""lotto  : String = """ + $show(lotto ));$skip(28); 


  val json = parse(lotto);System.out.println("""json  : org.json4s.JValue = """ + $show(json ));$skip(35); 

  val jValue = json \\ "lotto-id";System.out.println("""jValue  : org.json4s.JValue = """ + $show(jValue ));$skip(26); val res$0 = 
  compact(render(jValue));System.out.println("""res0: String = """ + $show(res$0));$skip(28); val res$1 = 

 HttpUtils.getAccess_token;System.out.println("""res1: String = """ + $show(res$1))}
}
