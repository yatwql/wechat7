package wechat7.routing
import wechat7.util._
import scala.xml._
import scala.collection._
class TextRouter extends Router {
  def getItems(articleList: List[Articles#TableElementType]): Seq[Node] = {
    articleList match {
      case article :: rest => {
        val item = <item>
                     <Title>{ article.title } </Title>
                     <Description>{ article.description }</Description>
                     <PicUrl>{ article.picUrl }</PicUrl>
                     <Url>{ article.url }</Url>
                   </item>

        item +: getItems(rest)
      }
      case Nil => Seq[Node]()
    }
  }
  override def responseImpl(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Node = {
    val nickname = getNickname(openId).get
    val Pattern = "(\\d+)".r
    requestContent match {
      case "news" => {
        val item1 = <item>
                      <Title>News for { nickname }</Title>
                      <Description>I love redwine</Description>
                      <PicUrl>{ Constants.REDWINE_PIC }</PicUrl>
                      <Url>{ Constants.SHOP_AT_DIANPING }</Url>
                    </item>

        val items = Seq(item1)
        WechatUtils.getNewsMsg(appUserId, openId, items)
      }
      case Pattern(actionKey) => {
        val articleList = getArticles(actionKey)
        println("articles size " + articleList.size)
       // val items = Seq[Node]()
        /*  val item = <item>
                     <Title>News till {actionKey} for { nickname } </Title>
                     <Description>I love redwine,{ nickname }</Description>
                     <PicUrl>{Constants.REDWINE_PIC}</PicUrl>
                      <Url>{Constants.SHOP_AT_DIANPING}</Url>
                   </item> */
        // val items = Seq(item,item,item)
 val items =getItems(articleList)

        println(items.size)

        WechatUtils.getNewsMsg(appUserId, openId, items)
      }

      case _ => {
        val responseContent = nickname + " say '" + requestContent + "'  "
        WechatUtils.getTextMsg(appUserId, openId, responseContent)
      }
    }
  }
}