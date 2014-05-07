package wechat7.repo

import wechat7.util._
trait ArticleRepo extends SlickRepo {

  import profile.simple._

  override def populateTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("articles") + " , " + super.populateTable(tableName)
        }
        case "actions" => {
          try {
            println("======================Insert actions into database ====================")
            actions.insert(Action("vote", "", "vote"))
            actions.insert(Action("投票", "", "vote"))
            actions.insert(Action("投稿", "", "articles\\add"))
            actions.insert(Action("articles", "", "articles\\add"))
            actions.insert(Action("vote21", "",  "vote22"))
            println("======================retrieve actions from database ====================")
            actions.list foreach println

            "Populate actions; "
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }
        case "articles" => {
          try {
            println("======================Insert articles into database ====================")
            articles.insert(Article("Test 题目1", "Description", "1", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("Test 2A", "New Description 2A", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("Test 题目2B", "New Description 2B", "2", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("New Title 3", "New Description", "3", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("红酒鉴赏小知识", "红酒鉴赏小知识", "11", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("南美农产品", "南美农产品", "12", "news", Constants.REDWINE_PIC, Constants.SHOP_AT_DIANPING))
            articles.insert(Article("智利", "智利", "vote21", "news", "", ""))
            articles.insert(Article("法国", "法国", "vote22", "news", "", ""))
            articles.insert(Article("巴拉圭", "巴拉圭", "vote23", "news", "", ""))
            articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "help", "text"))
            articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "帮助", "text"))
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

  def getNews(count: Int = 20) = {
    conn.dbObject withSession { implicit session: Session =>
      val query = articles.filter(_.msgTyp.===("news"))
      val result = query.list()
      // result
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
  
  def getAction(actionKey: String) :Action = {
    conn.dbObject withSession { implicit session: Session =>
      val query = actions.filter(_.actionKey.===(actionKey))
      val result = query.list()
      result.last
    }
  }


}