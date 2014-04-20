package org.dragonstudio.wechat
import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.net._
import org.dragonstudio.wechat.util._
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


  val json = parse(lotto)                         //> json  : org.json4s.JValue = JObject(List((lotto,JObject(List((lotto-id,JInt(
                                                  //| 5)), (winning-numbers,JArray(List(JInt(2), JInt(45), JInt(34), JInt(23), JIn
                                                  //| t(7), JInt(5), JInt(3)))), (winners,JArray(List(JObject(List((winner-id,JInt
                                                  //| (23)), (numbers,JArray(List(JInt(2), JInt(45), JInt(34), JInt(23), JInt(3), 
                                                  //| JInt(5)))))), JObject(List((winner-id,JInt(54)), (numbers,JArray(List(JInt(5
                                                  //| 2), JInt(3), JInt(12), JInt(11), JInt(18), JInt(22))))))))))))))

  val jValue = json \\ "lotto-id"                 //> jValue  : org.json4s.JValue = JInt(5)
  compact(render(jValue))                         //> res0: String = 5

 HttpUtils.getAccess_token                        //> {"access_token":"E-VeHVBDkX0Dk7A0lNwdn2nMswqQkEEV0O_Mr-uweqzlIbUYKxCO8Ze-xzO
                                                  //| PStlBdgsg0EWm96Sq79K_pbfLYNwmnUITmBGWaJagaOuJCkXumRADKXsvXWB28iCET8qLFQHoh2F
                                                  //| 4JpIyxMRpBxdArw","expires_in":7200}
                                                  //| "E-VeHVBDkX0Dk7A0lNwdn2nMswqQkEEV0O_Mr-uweqzlIbUYKxCO8Ze-xzOPStlBdgsg0EWm96S
                                                  //| q79K_pbfLYNwmnUITmBGWaJagaOuJCkXumRADKXsvXWB28iCET8qLFQHoh2F4JpIyxMRpBxdArw"
                                                  //| 
                                                  //| res1: String = "E-VeHVBDkX0Dk7A0lNwdn2nMswqQkEEV0O_Mr-uweqzlIbUYKxCO8Ze-xzOP
                                                  //| StlBdgsg0EWm96Sq79K_pbfLYNwmnUITmBGWaJagaOuJCkXumRADKXsvXWB28iCET8qLFQHoh2F4
                                                  //| JpIyxMRpBxdArw"
}