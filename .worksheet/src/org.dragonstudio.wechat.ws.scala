package wechat7



object ws {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(90); 
  println("Welcome to the Scala worksheet");$skip(61); 
  val signature = "1ed54a688723d48dd90e745d595f76710f3e177f";System.out.println("""signature  : String = """ + $show(signature ));$skip(31); 
  val timestamp = "1397838353";System.out.println("""timestamp  : String = """ + $show(timestamp ));$skip(27); 
  val nonce = "1462313406";System.out.println("""nonce  : String = """ + $show(nonce ));$skip(38); 
  val echostr = "3579823283840439871";System.out.println("""echostr  : String = """ + $show(echostr ));$skip(23); 
  val token = "WANGQL";System.out.println("""token  : String = """ + $show(token ));$skip(63); 

  val tmpArr = Array(token, timestamp, nonce).sortWith(_ < _);System.out.println("""tmpArr  : Array[String] = """ + $show(tmpArr ));$skip(32); 

  val tmpStr = tmpArr.mkString;System.out.println("""tmpStr  : String = """ + $show(tmpStr ));$skip(60); 

  val md = java.security.MessageDigest.getInstance("SHA1");System.out.println("""md  : java.security.MessageDigest = """ + $show(md ));$skip(39); 

  val ha = md.digest(tmpStr.getBytes);System.out.println("""ha  : Array[Byte] = """ + $show(ha ));$skip(70); val res$0 = 

  md.digest(tmpStr.getBytes("UTF-8")).map("%02x".format(_)).mkString;System.out.println("""res0: String = """ + $show(res$0))}

 

}
