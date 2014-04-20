package org.dragonstudio.wechat
import scala.xml._
import java.util.Date
object xml {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
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
    </books>"""                                   //> books  : String = <books>
                                                  //|       <book>
                                                  //|         <title>a</title>
                                                  //|         <price>100</price>
                                                  //|       </book>
                                                  //|       <book>
                                                  //|         <title>b</title>
                                                  //|         <price>200</price>
                                                  //|       </book>
                                                  //|       <book>
                                                  //|         <title>c</title>
                                                  //|         <price>300</price>
                                                  //|       </book>
                                                  //|     </books>

  val booksXml = XML.loadString(books)            //> booksXml  : scala.xml.Elem = <books>
                                                  //|       <book>
                                                  //|         <title>a</title>
                                                  //|         <price>100</price>
                                                  //|       </book>
                                                  //|       <book>
                                                  //|         <title>b</title>
                                                  //|         <price>200</price>
                                                  //|       </book>
                                                  //|       <book>
                                                  //|         <title>c</title>
                                                  //|         <price>300</price>
                                                  //|       </book>
                                                  //|     </books>
  val prices: Seq[(String, Double)] = for {
    book <- booksXml \ "book"
    title <- book \ "title"
    price <- book \ "price"
  } yield (title.text, price.text.toDouble)       //> prices  : Seq[(String, Double)] = List((a,100.0), (b,200.0), (c,300.0))
  // do something with prices
  prices foreach println                          //> (a,100.0)
                                                  //| (b,200.0)
                                                  //| (c,300.0)

  val wxr = """<xml>
<ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
<FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
<Content><![CDATA[dddd]]></Content>
<CreateTime>1397931201</CreateTime>
<MsgType><![CDATA[text]]></MsgType>
<MsgId>6004068790553573078</MsgId>
</xml>"""                                         //> wxr  : String = <xml>
                                                  //| <ToUserName><![CDATA[gh_c2bb951675bb]]></ToUserName>
                                                  //| <FromUserName><![CDATA[oIySzjrizSaAyqnlB57ggb0j2WNc]]></FromUserName>
                                                  //| <Content><![CDATA[dddd]]></Content>
                                                  //| <CreateTime>1397931201</CreateTime>
                                                  //| <MsgType><![CDATA[text]]></MsgType>
                                                  //| <MsgId>6004068790553573078</MsgId>
                                                  //| </xml>

  val wxl = XML.loadString(wxr)                   //> wxl  : scala.xml.Elem = <xml>
                                                  //| <ToUserName>gh_c2bb951675bb</ToUserName>
                                                  //| <FromUserName>oIySzjrizSaAyqnlB57ggb0j2WNc</FromUserName>
                                                  //| <Content>dddd</Content>
                                                  //| <CreateTime>1397931201</CreateTime>
                                                  //| <MsgType>text</MsgType>
                                                  //| <MsgId>6004068790553573078</MsgId>
                                                  //| </xml>
  val toUser = (wxl \ "ToUserName").text          //> toUser  : String = gh_c2bb951675bb
  val fromUser = (wxl \ "FromUserName").text      //> fromUser  : String = oIySzjrizSaAyqnlB57ggb0j2WNc

  val now = new Date().getTime()                  //> now  : Long = 1398002701397

  val response =
    <xml>
      <ToUserName>{ fromUser }</ToUserName>
      <FromUserName>{ toUser }</FromUserName>
      <Content><![CDATA[ccc]]></Content>
      <CreateTime>{ now }</CreateTime>
      <MsgType><![CDATA[text]]></MsgType>
    </xml>                                        //> response  : scala.xml.Elem = <xml>
                                                  //|       <ToUserName>oIySzjrizSaAyqnlB57ggb0j2WNc</ToUserName>
                                                  //|       <FromUserName>gh_c2bb951675bb</FromUserName>
                                                  //|       <Content>ccc</Content>
                                                  //|       <CreateTime>1398002701397</CreateTime>
                                                  //|       <MsgType>text</MsgType>
                                                  //|     </xml>

}