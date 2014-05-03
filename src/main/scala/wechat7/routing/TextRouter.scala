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
  override def responseImpl(openId: String, appUserId: String, msgType: String, requestXml: Option[Elem], requestContent: String): Option[Node] = {
    val nickname = getNickname(openId).get
    val Pattern = "(\\d+)".r

    val articleList = getArticles(requestContent)
    articleList match {
      case a :: b => {
        val items = getItems(articleList)
        Some(WechatUtils.getNewsMsg(appUserId, openId, items))
      }
      case Nil => {
        dontknow(openId, appUserId, nickname, requestContent)
      }
    }

  }
  def dontknow(openId: String, appUserId: String, nickname: String, requestContent: String): Option[Node] = {
    val responseContent = nickname + " ,I can not understand '" + requestContent + "', try to type 'help'  "
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }
}