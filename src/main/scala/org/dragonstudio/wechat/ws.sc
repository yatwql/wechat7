package org.dragonstudio.wechat



object ws {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val signature = "1ed54a688723d48dd90e745d595f76710f3e177f"
                                                  //> signature  : String = 1ed54a688723d48dd90e745d595f76710f3e177f
  val timestamp = "1397838353"                    //> timestamp  : String = 1397838353
  val nonce = "1462313406"                        //> nonce  : String = 1462313406
  val echostr = "3579823283840439871"             //> echostr  : String = 3579823283840439871
  val token = "WANGQL"                            //> token  : String = WANGQL

  val tmpArr = Array(token, timestamp, nonce).sortWith(_ < _)
                                                  //> tmpArr  : Array[String] = Array(1397838353, 1462313406, WANGQL)

  val tmpStr = tmpArr.mkString                    //> tmpStr  : String = 13978383531462313406WANGQL

  val md = java.security.MessageDigest.getInstance("SHA1")
                                                  //> md  : java.security.MessageDigest = SHA1 Message Digest from SUN, <initializ
                                                  //| ed>
                                                  //| 

  val ha = md.digest(tmpStr.getBytes)             //> ha  : Array[Byte] = Array(30, -43, 74, 104, -121, 35, -44, -115, -39, 14, 11
                                                  //| 6, 93, 89, 95, 118, 113, 15, 62, 23, 127)

  md.digest(tmpStr.getBytes("UTF-8")).map("%02x".format(_)).mkString
                                                  //> res0: String = 1ed54a688723d48dd90e745d595f76710f3e177f

 

}