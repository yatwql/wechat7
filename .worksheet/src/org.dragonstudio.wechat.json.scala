package org.dragonstudio.wechat
import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._
import org.dragonstudio.wechat.util._
object json {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(205); 
  println("Welcome to the Scala worksheet")
  case class tokenClass(access_token:String,expires_in:String);$skip(104); 
   implicit val formats = DefaultFormats;System.out.println("""formats  : org.json4s.DefaultFormats.type = """ + $show(formats ));$skip(35); 
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
""";System.out.println("""lotto  : String = """ + $show(lotto ));$skip(27); 

  val json = parse(lotto);System.out.println("""json  : org.json4s.JValue = """ + $show(json ));$skip(35); 

  val jValue = json \\ "lotto-id";System.out.println("""jValue  : org.json4s.JValue = """ + $show(jValue ));$skip(26); val res$0 = 
  compact(render(jValue));System.out.println("""res0: String = """ + $show(res$0));$skip(288); 

  //  val token = WechatUtils.getAccess_token

  // println(" token -> " + token)

  val j = """{"access_token":"XmFuAHgmpy5jPiIF8a_zoEJvj5kkNXkDvqKxkq53F1rxvUAKAl6r5R-p3KSZAS5q5Ev3cL1a2y-YWALb18JRZRSbGj898M97zKkUVaDSINE-QOPTxiaPyryw1xRJf3PVNkbOg4scayzd30iMPdMnLQ","expires_in":7200}""";System.out.println("""j  : String = """ + $show(j ));$skip(120); 
      

                                           
                                                  val j2 = parse(j);System.out.println("""j2  : org.json4s.JValue = """ + $show(j2 ));$skip(128); 
                                                  
                                                 val j3 =j2 \\"access_token";System.out.println("""j3  : org.json4s.JValue = """ + $show(j3 ));$skip(178); 
                                                  
                                                  
                                                val t=  compact(render(j3));System.out.println("""t  : String = """ + $show(t ));$skip(76); 
                                                  println(" token -> " + t);$skip(128); 
                                                  
                                                  val t2= j3.extract[String];System.out.println("""t2  : String = """ + $show(t2 ));$skip(78); 
                                                  println(" token2 -> " + t2)}
                                                  
                                                 
}
