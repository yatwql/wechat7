package wechat7.persistent


trait ArticleRepo extends SlickRepo  {
 
 import profile.simple._
 
 def addArticle(title: String, description: String, actionKey:String,msgTyp:String ="text",picUrl: String="", url: String="", id: Int = 0) {
    conn.dbObject withSession { implicit session: Session =>
      articles.insert(Article(title, description, actionKey, msgTyp, picUrl, url, id))
    }
  }
 
 def getArticles(actionKey: String): Unit = {
    conn.dbObject withSession { implicit session: Session =>
      val query = articles.filter(_.actionKey.===(actionKey))
    val result= query.list()
    result
    }
  }

}