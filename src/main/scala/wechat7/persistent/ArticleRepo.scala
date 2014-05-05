package wechat7.persistent

import wechat7.util._
trait ArticleRepo extends SlickRepo {

  import profile.simple._

  override def populateTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("articles") + " , " + super.populateTable(tableName)
        }

        case "articles" => {
           try {
          println("======================Insert articles into database ====================")
          articles.insert(Article("题目1", "Description", "1", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
          articles.insert(Article("New Title 2A", "New Description 2A", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
          articles.insert(Article("题目2", "New Description 2B", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
          articles.insert(Article("New Title 3", "New Description", "3", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
          articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "help", "text"))
          println("======================retrieve articles from database ====================")
          articles.list foreach println

          "Populate articles; "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case _ => super.populateTable(tableName)
      }
    }
  }

  def addArticle(title: String, description: String, actionKey: String, msgTyp: String = "text", picUrl: String = "", url: String = "", id: Int = 0) {
    conn.dbObject withSession { implicit session: Session =>
      articles.insert(Article(title, description, actionKey, msgTyp, picUrl, url, id))
    }
  }

  def getArticles(actionKey: String) = {
    conn.dbObject withSession { implicit session: Session =>
      val query = articles.filter(_.actionKey.===(actionKey))
      val result = query.list()
      result
    }
  }

}