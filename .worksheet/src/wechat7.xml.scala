package wechat7
import scala.xml._
import java.util.Date
import wechat7.util._
import scala.xml.transform._
object xml {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(164); 
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

  val wxl = XML.loadString(wxr);System.out.println("""wxl  : scala.xml.Elem = """ + $show(wxl ));$skip(41); 
  val toUser = (wxl \ "ToUserName").text;System.out.println("""toUser  : String = """ + $show(toUser ));$skip(45); 
  val fromUser = (wxl \ "FromUserName").text;System.out.println("""fromUser  : String = """ + $show(fromUser ));$skip(34); 

  val now = new Date().getTime();System.out.println("""now  : Long = """ + $show(now ));$skip(279); 

  val response =
    <xml>
      <ToUserName>{ fromUser }</ToUserName>
      <FromUserName>{ toUser }</FromUserName>
      <Content><![CDATA[ccc]]></Content>
      <CreateTime>{ now }</CreateTime>
      <articles></articles>
      <MsgType><![CDATA[text]]></MsgType>
    </xml>;System.out.println("""response  : scala.xml.Elem = """ + $show(response ));$skip(37); val res$0 = 

  (response \\ "FromUserName").text;System.out.println("""res0: String = """ + $show(res$0));$skip(30); 

  val i1 = <item>cc1 </item>;System.out.println("""i1  : scala.xml.Elem = """ + $show(i1 ));$skip(34); 
  val i2 = <item> lsdkjf22</item>;System.out.println("""i2  : scala.xml.Elem = """ + $show(i2 ));$skip(27); 

  val items = Seq(i1, i2);System.out.println("""items  : Seq[scala.xml.Elem] = """ + $show(items ));$skip(63); 


  val dd = XmlUtils.addChildren(response, "articles", items);System.out.println("""dd  : scala.xml.Node = """ + $show(dd ));$skip(104); 
                                                  
  val m=WechatUtils.getNewsMsg("a", "b", "c", items);System.out.println("""m  : String = """ + $show(m ));$skip(475); 
                       
                       
                                           
                                               
    val oldXML =
      <xml>
        <ToUserName>a</ToUserName>
        <FromUserName>b</FromUserName>
        <Content>c</Content>
        <CreateTime>now</CreateTime>
        <MsgType><![CDATA[news]]></MsgType>
        <ArticleCount>2</ArticleCount>
        <Articles>
        </Articles>
        <FuncFlag>0</FuncFlag>
      </xml>;System.out.println("""oldXML  : scala.xml.Elem = """ + $show(oldXML ));$skip(67); 

    val message = XmlUtils.addChildren(oldXML, "Articles", items);System.out.println("""message  : scala.xml.Node = """ + $show(message ))}
                                                  
}
