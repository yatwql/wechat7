package wechat7.repo

import wechat7.util._
trait ActionRepo extends SlickRepo {

  import profile.simple._

  override def populateTable(tableName: String = "all"): String = {
    println("execute ActionRepo")
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("articles") + " , " + populateTable("actions") + " , " + super.populateTable(tableName)
        }
        case "actions" => {
          try {
            println("======================Insert actions into database ====================")
            actions.insert(Action("vote", "vote", ""))
            actions.insert(Action("投票", "vote", ""))
            actions.insert(Action("投稿", "", "articles\\add"))
            actions.insert(Action("articles", "", "articles\\add"))
            actions.insert(Action("vote21", "vote21", "voting21"))
            actions.insert(Action("vote22", "vote22", "voting22"))
            actions.insert(Action("vote23", "vote23", "voting23"))
            actions.insert(Action("history", "history", ""))
            actions.insert(Action("list", "history", ""))
            actions.insert(Action("help", "help", ""))
            actions.insert(Action("帮助", "help", ""))
            println("======================retrieve actions from database ====================")
            actions.list foreach println

            "<ActionRepo>Populate actions; "
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
            articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "help", "text"))
            articles.insert(Article("帮助", "打help出此页面,history列出最新二十篇文章,vote参加投票 ", "帮助", "text"))
            println("======================retrieve articles from database ====================")
            articles.list foreach println

            "<ActionRepo>Populate articles; "
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
  def getArticleList(drop: Int) = {
    conn.dbObject withSession { implicit session: Session =>
      val query = for (a <- articles) yield (a.actionKey, a.title)
      val result = query.drop(drop).list()
      result
    }
  }

  def getArticles(actionKey: String) = {
    conn.dbObject withSession { implicit session: Session =>
      val query = articles.filter(_.actionKey.===(actionKey))
      val result = query.list()
      result
    }
  }

  def getAction(actionKey: String): Option[Action] = {
    conn.dbObject withSession { implicit session: Session =>
      val query = actions.filter(_.actionKey.===(actionKey))
      val result = query.list()
      result match {
        case a :: _ => Some(result.last)
        case Nil => None
        case _ => None
      }
    }
  }

}