package wechat7.act

import wechat7.repo._
import wechat7.util._
import scala.xml._
import scala.collection._

trait ActionRouter extends ArticleRepo with VoteRepo {
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

  def response(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String): Option[Node] = {
    val Pattern = "(\\d+)".r
    val articleList = getArticles(actionKey)
    articleList match {
      case a :: b => {
        val items = getItems(articleList)
        Some(WechatUtils.getNewsMsg(appUserId, openId, items))
      }
      case Nil => {
        dontknow(openId, appUserId, nickname, actionKey)
      }
    }

  }

  def dontknow(openId: String, appUserId: String, nickname: String, requestContent: String): Option[Node] = {
    val responseContent = nickname + " ,I can not understand '" + requestContent + "', try to type 'help'  "
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }

}
