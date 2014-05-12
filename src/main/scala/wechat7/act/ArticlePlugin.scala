package wechat7.act
import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo.ActionRepo
import wechat7.util.WechatUtils

trait ArticlePlugin extends ActionRepo with Plugin {
  import system.dispatcher

  def getNewsItems(actionKey: String): Seq[Node] = {
    Router.articleCache(actionKey) {
      val articleList = getArticles(actionKey)
      val items = articleList match {
        case a :: b => {
          getNewsItems(articleList)

        }
        case Nil => {
          Seq[Node]()
        }
      }
      items
    }.await
  }
  def getNewsItems(articleList: List[Articles#TableElementType]): Seq[Node] = {
    articleList match {
      case article :: rest => {
        val item = <item>
                     <Title>{ article.title } </Title>
                     <Description>{ article.description }</Description>
                     <PicUrl>{ article.picUrl }</PicUrl>
                     <Url>{ article.url }</Url>
                   </item>

        item +: getNewsItems(rest)

      }
      case Nil => Seq[Node]()
    }
  }
  def getArticleTopics(openId: String, nickname: String, appUserId: String): Option[Node] = {
    val articleList = getArticleList(20)
    val desc = splitListIntoDesc(articleList)
    val responseContent = nickname + ". 最近的 "+articleList.size+" 篇文章列表 -> " + desc 
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }
  def getArticle(openId: String, nickname: String, appUserId: String, actionKey: String, requestContent: String): Option[Node] = {

    val items = getNewsItems(actionKey)
    items match {
      case a :: b => {
        Some(WechatUtils.getNewsMsg(appUserId, openId, items))
      }
      case _ => {
        dontknow(openId, appUserId, nickname, actionKey)
      }
    }

  }

 
}