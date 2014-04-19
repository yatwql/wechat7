package org.dragonstudio.wechat
import scala.xml._
object xml {
  println("Welcome to the Scala worksheet")
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
    </books>"""

  val booksXml = XML.loadString(books)
  val prices: Seq[(String, Double)] = for {
    book <- booksXml \ "book"
    title <- book \ "title"
    price <- book \ "price"
  } yield (title.text, price.text.toDouble)
  // do something with prices
  prices foreach println

  val wxr = """<xml>
<ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
<FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
<Content><![CDATA[dddd]]></Content>
<CreateTime>1397931201</CreateTime>
<MsgType><![CDATA[text]]></MsgType>
<MsgId>6004068790553573078</MsgId>
</xml>"""

  val wxl = XML.loadString(wxr)
                                        //    val toUser=     ( wxl \ "ToUserName").text
                                                  
                                                  val b: Seq[(String, String, Long,String)] = for {
    toUser  <- wxl \ "ToUserName"
    fromUser <- wxl \ "FromUserName"
    createTime <- wxl \ "CreateTime"
    msgType <= wxl \"MsgType"
  } yield (toUser.text, fromUser.text,createTime.text.toLong,msgType.text)
}