package wechat7.act

import scala.collection.Seq
import scala.xml.Node

import akka.actor.ActorSystem
import spray.caching.ValueMagnet.fromAny
import spray.util.pimpFuture
import wechat7.repo.ArticleRepo
import wechat7.util.WechatUtils

trait ActionRouter extends ArticleRepo with Plugin with ActionPlugin {
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
  override def process(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String) {
    super.process(openId, nickname, appUserId, msgType, actionKey)
    println(" process from ActionRouter ")
  }

  def response(openId: String, nickname: String, appUserId: String, msgType: String, actionKey: String): Option[Node] = {
    val Pattern = "(\\d+)".r
    process(openId, nickname, appUserId, msgType, actionKey)
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

  def dontknow(openId: String, appUserId: String, nickname: String, requestContent: String): Option[Node] = {
    val responseContent = nickname + " ,I can not understand '" + requestContent + "', try to type 'help'  "
    Some(WechatUtils.getTextMsg(appUserId, openId, responseContent))
  }

}
