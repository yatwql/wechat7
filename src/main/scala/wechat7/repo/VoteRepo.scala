package wechat7.repo

trait VoteRepo extends SlickRepo {

  import profile.simple._

  override def populateTable(tableName: String = "all"): String = {
    println("execute VoteRepo " + tableName)
    conn.dbObject withSession { implicit session: Session =>
      tableName match {
        case "all" => {
          populateTable("voteThreads") + " , " + populateTable("voteOptions") + " , " + populateTable("voteResults") + " , " + super.populateTable(tableName)
        }
        case "voteThreads" => {
          try {
            println("======================Insert voteThreads into database ====================")
            voteThreads.insert(VoteThread(21, "红酒调查(地点)", "您喜欢以下哪个产地的红酒: ", 2))
            voteThreads.insert(VoteThread(22, "红酒调查(口味)", "您喜欢哪种葡萄酒: ", 1))
            voteThreads.insert(VoteThread(23, "红酒调查(价格)", "您觉得可接受的红酒价格为: ", 1))
            println("======================retrieve voteThreads from database ====================")
            voteThreads.list foreach println
            "<VoteRepo>Populate voteThreads;"
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }
        case "voteOptions" => {
          try {
            println("======================Insert voteOptions into database ====================")
            voteOptions.insert(VoteOption(21, "1", "智利"))
            voteOptions.insert(VoteOption(21, "2", "法国"))
            voteOptions.insert(VoteOption(21, "3", "巴拉圭"))
            voteOptions.insert(VoteOption(21, "4", "中国"))
            voteOptions.insert(VoteOption(21, "5", "其他"))
            voteOptions.insert(VoteOption(22, "1", "白葡萄酒"))
            voteOptions.insert(VoteOption(22, "2", "红葡萄酒"))
            voteOptions.insert(VoteOption(23, "1", "100以下"))
            voteOptions.insert(VoteOption(23, "2", "200至500"))
            voteOptions.insert(VoteOption(23, "3", "我不想花钱"))
            voteOptions.insert(VoteOption(23, "4", "大爷有钱,好喝就行"))
            println("======================retrieve voteOptions from database ====================")
            voteOptions.list foreach println
            "<VoteRepo>Populate voteOptions;"
          } catch {
            case ex: Exception => println(ex.getMessage); ""
          }
        }

        case "voteResults" => {
          ""
        }

        case _ => super.populateTable(tableName)
      }
    }
  }

  def getVoteThread(voteId: Int):Option[VoteThread] = {
    conn.dbObject withSession { implicit session: Session =>
      val result = voteThreads.filter(_.voteId === voteId).list
      result match {
        case a :: _ => Some(result.last)
        case _ => None
      }
    }
  }
  
  def getvoteTopics(take:Int) = {
      conn.dbObject withSession { implicit session: Session =>
       val query = for (a <-voteThreads) yield ("vote"+a.voteId,a.name)
      val result = query.take(take).list()
      result
    }
  }
  
   def getVoteOptions(voteId: Int):List[VoteOptions#TableElementType] = {
    conn.dbObject withSession { implicit session: Session =>
      voteOptions.filter(_.voteId === voteId).list
    }
  }
   
   def getVoteResult(openId:String,voteId:Int) = {
      conn.dbObject withSession { implicit session: Session =>
      val result=voteResults.filter(_.openId===openId).filter(_.voteId===voteId).list
      result match {
        case a::_ => Some(result.last)
        case _ => None
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
      val c = voteResults.filter(_.openId === openId).filter(_.voteId === voteId).map(_.option)
      c.update(option)

    }
  }
}