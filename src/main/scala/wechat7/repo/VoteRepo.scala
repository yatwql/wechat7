package wechat7.repo

trait VoteRepo extends SlickRepo {

  import profile.simple._

  override def populateTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("voteTopics") + " , " + populateTable("voteResults") + " , " + super.populateTable(tableName)
        }
        case "voteTopics" => {
          try {
            println("======================Insert voteTopics into database ====================")
            voteTopics.insert(VoteTopic("redwine", "Favour contry"))
            println("======================retrieve voteTopics from database ====================")
            voteTopics.list foreach println
            "Populate voteTopics;"
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case _ => super.populateTable(tableName)
      }
    }
  }

  def addVoteResult(openId: String, voteId: Int, option: String, fromLocationX: String = "", fromLocationY: String = "") {
    conn.dbObject withSession { implicit session: Session =>
      voteResults.insert(VoteResult(openId, voteId, option, fromLocationX, fromLocationY))
    }
  }
  
   def updateVoteResult(openId: String, voteId: Int, option: String) {
    conn.dbObject withSession { implicit session: Session =>
      val c=voteResults.filter(_.openId===openId).filter(_.voteId===voteId).map(_.option)
      c.update(option)
    
    }
  }
}