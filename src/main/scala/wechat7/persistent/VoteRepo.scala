package wechat7.persistent

trait VoteRepo extends SlickRepo  {
 
 import profile.simple._
 def addVoteResult(openId: String, voteId: Int, option:String,fromLocationX: String = "", fromLocationY: String = "") {
    conn.dbObject withSession { implicit session: Session =>
      voteResults.insert(VoteResult(openId, voteId, option, fromLocationX, fromLocationY))
    }
  }
}