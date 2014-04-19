package org.dragonstudio.wechat
import scala.xml._
object xml {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(107); 
  println("Welcome to the Scala worksheet");$skip(278); 
  val books = """<books>
      <book>
        <title>a</title>
        <price>100</price>
      </book>
      <book>
        <title>b</title>
        <price>200</price>
      </book>
      <book>
        <title>c</title>
        <price>300</price>
      </book>
    </books>""";System.out.println("""books  : String = """ + $show(books ));$skip(40); 

  val booksXml = XML.loadString(books);System.out.println("""booksXml  : scala.xml.Elem = """ + $show(booksXml ));$skip(174); 
  val prices: Seq[(String, Double)] = for {
    book <- booksXml \ "book"
    title <- book \ "title"
    price <- book \ "price"
  } yield (title.text, price.text.toDouble);System.out.println("""prices  : Seq[(String, Double)] = """ + $show(prices ));$skip(55); 
  // do something with prices
  prices foreach println;$skip(298); 

  val wxr = """<xml>
<ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
<FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
<Content><![CDATA[dddd]]></Content>
<CreateTime>1397931201</CreateTime>
<MsgType><![CDATA[text]]></MsgType>
<MsgId>6004068790553573078</MsgId>
</xml>""";System.out.println("""wxr  : String = """ + $show(wxr ));$skip(33); 

  val wxl = XML.loadString(wxr);System.out.println("""wxl  : scala.xml.Elem = """ + $show(wxl ));$skip(453); 
                                        //    val toUser=     ( wxl \ "ToUserName").text
                                                  
                                                  val b: Seq[(String, String, Long,String)] = for {
    toUser  <- wxl \ "ToUserName"
    fromUser <- wxl \ "FromUserName"
    createTime <- wxl \ "CreateTime"
    msgType <= wxl \"MsgType"
  } yield (toUser.text, fromUser.text,createTime.text.toLong,msgType.text);System.out.println("""b  : Seq[(String, String, Long, String)] = """ + $show(b ))}
}
