package org.dragonstudio.wechat
import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._
object json {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val appId = "wx97c2cba93843a8e6"                //> appId  : String = wx97c2cba93843a8e6
  val appSecret = "4f488c849ddf23c83ef97e5b8482b8a1"
                                                  //> appSecret  : String = 4f488c849ddf23c83ef97e5b8482b8a1
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
"""                                               //> lotto  : String = "
                                                  //| {
                                                  //|   "lotto":{
                                                  //|     "lotto-id":5,
                                                  //|     "winning-numbers":[2,45,34,23,7,5,3],
                                                  //|     "winners":[ {
                                                  //|       "winner-id":23,
                                                  //|       "numbers":[2,45,34,23,3, 5]
                                                  //|     },{
                                                  //|       "winner-id" : 54 ,
                                                  //|       "numbers":[ 52,3, 12,11,18,22 ]
                                                  //|     }]
                                                  //|   }
                                                  //| }
                                                  //| "

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

  }                                               //> getAccess_token: ()String

  val json = parse(lotto)                         //> json  : org.json4s.JValue = JObject(List((lotto,JObject(List((lotto-id,JInt
                                                  //| (5)), (winning-numbers,JArray(List(JInt(2), JInt(45), JInt(34), JInt(23), J
                                                  //| Int(7), JInt(5), JInt(3)))), (winners,JArray(List(JObject(List((winner-id,J
                                                  //| Int(23)), (numbers,JArray(List(JInt(2), JInt(45), JInt(34), JInt(23), JInt(
                                                  //| 3), JInt(5)))))), JObject(List((winner-id,JInt(54)), (numbers,JArray(List(J
                                                  //| Int(52), JInt(3), JInt(12), JInt(11), JInt(18), JInt(22))))))))))))))

  val jValue = json \\ "lotto-id"                 //> jValue  : org.json4s.JValue = JInt(5)
  compact(render(jValue))                         //> res0: String = 5

  //getAccess_token

  val a = """  {"access_token":"DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mGN3x_L5KDLP8Waj5GH1VtOXVSnEMCYm2BVifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuNOKkk3jjlUx6555rvcRSss7miGdUWxchKUw","expires_in":7200}"""
                                                  //> a  : String = "  {"access_token":"DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mG
                                                  //| N3x_L5KDLP8Waj5GH1VtOXVSnEMCYm2BVifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuN
                                                  //| OKkk3jjlUx6555rvcRSss7miGdUWxchKUw","expires_in":7200}"

  val b = parse(a) \\ "access_token"              //> b  : org.json4s.JValue = JString(DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mGN
                                                  //| 3x_L5KDLP8Waj5GH1VtOXVSnEMCYm2BVifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuNO
                                                  //| Kkk3jjlUx6555rvcRSss7miGdUWxchKUw)

  val c = compact(render(b))                      //> c  : String = "DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mGN3x_L5KDLP8Waj5GH1V
                                                  //| tOXVSnEMCYm2BVifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuNOKkk3jjlUx6555rvcRS
                                                  //| ss7miGdUWxchKUw"
                                                 c.getClass
                                                  //> res1: Class[?0] = class java.lang.String
                                                  

  println(c)                                      //> "DIcV2Mld5ojbPQfUgDy4aPmXIxHfE14hamMvKi3mGN3x_L5KDLP8Waj5GH1VtOXVSnEMCYm2BV
                                                  //| ifj5Y5s9tNsbZKRyvnk2iKEUgIwIFrMkPnyjDrSQuNOKkk3jjlUx6555rvcRSss7miGdUWxchKU
                                                  //| w"
                                                  
                                                  val d=getAccess_token
                                                  //> {"access_token":"BEE5U321XkuaxCrair3drFljGPBpaMDnNJ3gdgO1PNkXoRC6nO-7MnqI3C
                                                  //| 0gpTOE4T2NPHzyM8JNi_Nbv_X6cacSmbprcFPj1F7-gZ9F4maWaYTCZ_z21cIHBA8xWruTYE_d_
                                                  //| B2A7GI1HLscBPqsdw","expires_in":7200}
                                                  //| d  : String = "BEE5U321XkuaxCrair3drFljGPBpaMDnNJ3gdgO1PNkXoRC6nO-7MnqI3C0g
                                                  //| pTOE4T2NPHzyM8JNi_Nbv_X6cacSmbprcFPj1F7-gZ9F4maWaYTCZ_z21cIHBA8xWruTYE_d_B2
                                                  //| A7GI1HLscBPqsdw"
                                                  d.getClass
                                                  //> res2: Class[?0] = class java.lang.String

  

}