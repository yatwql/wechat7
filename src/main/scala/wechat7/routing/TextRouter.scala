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
        articleList match {
          case a :: b => {
            val items = getItems(articleList)
            WechatUtils.getNewsMsg(appUserId, openId, items)
          }
          case b :: Nil => {
            val items = getItems(articleList)
            WechatUtils.getNewsMsg(appUserId, openId, items)
          }
          case Nil => {
            val responseContent = nickname + " ,I can not get the aticle for '" + actionKey + "'  "
            WechatUtils.getTextMsg(appUserId, openId, responseContent)
          }
        }

      }

      case _ => {
        val responseContent = nickname + " say '" + requestContent + "'  "
        WechatUtils.getTextMsg(appUserId, openId, responseContent)
      }
    }
  }
}