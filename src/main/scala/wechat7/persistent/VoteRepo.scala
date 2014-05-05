package wechat7.persistent

trait VoteRepo extends SlickRepo {

  import profile.simple._

  override def createTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          createTable("voteTopics") + " , " + createTable("voteResults") + " , " + super.createTable(tableName)
        }
        case "voteTopics" => {
         doCreate(voteTopics)
        }
        case "voteResults" => {
          doCreate(voteResults)
        }

        case _ => super.createTable(tableName)
      }
    }
  }

  override def dropTable(tableName: String = "all"): String = {
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          dropTable("voteTopics") + " , " + dropTable("voteResults") + " , " + super.dropTable(tableName)
        }
        case "voteTopics" => {
         doDrop(voteResults)
        }
        case "voteResults" => {
          doDrop(voteResults)
        }

        case _ => super.dropTable(tableName)
      }
    }
  }

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
            "voteTopics"
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
}