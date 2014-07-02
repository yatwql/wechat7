package wechat7.repo
import wechat7.repo._
object my {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(98); 
  println("Welcome to the Scala worksheet")
  
  class VoteRepoImpl extends VoteRepo {

  };$skip(82); 
  val voteRepo = new VoteRepoImpl;System.out.println("""voteRepo  : wechat7.repo.my.VoteRepoImpl = """ + $show(voteRepo ));$skip(46); 
  
  
  val vote = voteRepo.getVoteThread(21);System.out.println("""vote  : Option[wechat7.repo.my.voteRepo.VoteThread] = """ + $show(vote ));$skip(137); 
                                                  
                                                  val c=voteRepo.getVoteGrpResult(22);System.out.println("""c  : scala.collection.immutable.Map[String,Int] = """ + $show(c ))}
                                                  
                                                  
               
                                                  
  
}
