package wechat7.controller

import wechat7.WechatAppStack
import wechat7.repo._
import wechat7.util._
import javax.servlet.annotation.MultipartConfig
import org.scalatra.servlet.FileUploadSupport
import org.scalatra.ScalatraServlet
import org.scalatra.servlet.FileItem

trait VoteController extends WechatAppStack {
  self: ScalatraServlet =>
  class VoteRepoImpl extends VoteRepo {

  }
  val voteRepo = new VoteRepoImpl

  get("/votes") {
    val list = voteRepo.getvoteTopics(20)
    ssp("/wechat/vote/votes", "title" -> "List Votes", "list" -> list)
  }

  get("/vote/:slug") {
    val slug = params("slug")
    val vote = voteRepo.getVoteThread(slug.toInt)
    ssp("/wechat/vote/view", "title" -> "Show Vote detail", "vote" -> vote)
  }

}