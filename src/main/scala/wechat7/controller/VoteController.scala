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

  get("/vote/list") {
    val list = voteRepo.getvoteTopics(20)
    ssp("/pages/vote/votes", "title" -> "List Votes", "list" -> list)
  }

  get("/vote/view/:slug") {
    val slug = params("slug")
    val vote = voteRepo.getVoteThread(slug.toInt)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/view", "title" -> "Show Vote detail ", "voteName" -> v.name, "description" -> v.description, "voteId" -> v.voteId, "voteMethod" -> v.voteMethod)
      }
      case _ => { "file not found" }
    }

  }

  get("/vote/edit/:slug") {
    val slug = params("slug")
    val vote = voteRepo.getVoteThread(slug.toInt)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/edit", "title" -> "Edit Vote detail ", "voteName" -> v.name, "description" -> v.description, "voteId" -> v.voteId, "voteMethod" -> v.voteMethod)
      }
      case _ => { "file not found" }
    }

  }

  post("/vote/update") {
    // val voteId = params("voteId")
    val voteName= params("voteName")
    val description=params("description")
    val voteMethod=params("voteMethod").toInt
    val vote = params.get("voteId") match {
      case Some(voteId) => {
        try{
          val id=voteId.toInt
          val v=voteRepo.updateVoteThread(voteName, description, voteMethod, id)
        }catch{
          case e=> None
        }
        voteRepo.getVoteThread(voteId.toInt)
      }
      case _ => {
       // voteRepo.getVoteThread(voteId.toInt)
      }
    }

    //val vote = voteRepo.getVoteThread(voteId.toInt)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/view", "title" -> "Show Vote detail ", "voteName" -> voteName, "description" -> description, "voteId" -> vote, "voteMethod" -> voteMethod, "message" -> "Successful Update!")
      }
      case _ => { "file not found" }
    }
  }

  get("/vote/list") {
    val list = voteRepo.getvoteTopics(20)
    ssp("/pages/vote/votes", "title" -> "List Votes", "list" -> list)
  }
  get("/vote/new") {
    ssp("/pages/vote/edit", "title" -> "Create Vote detail")
  }

}