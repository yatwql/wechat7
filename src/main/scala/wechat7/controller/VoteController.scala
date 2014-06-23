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
    val voteOptions = voteRepo.getVoteOptionsToTuples(slug.toInt)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/view", "title" -> "Show Vote detail ", "voteName" -> v.name, "description" -> v.description, "voteId" -> v.voteId, "voteMethod" -> v.voteMethod, "voteOptions" -> voteOptions)
      }
      case _ => { "file not found" }
    }

  }

  get("/vote/edit/:slug") {
    val slug = params("slug")
    val vote = voteRepo.getVoteThread(slug.toInt)
    val voteOptions = voteRepo.getVoteOptionsToTuples(slug.toInt)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/edit", "title" -> "Edit Vote detail ", "voteName" -> v.name, "description" -> v.description, "voteId" -> v.voteId, "voteMethod" -> v.voteMethod, "voteOptions" -> voteOptions)
      }
      case _ => { "file not found" }
    }

  }

  get("/voteId/result/:slug") {
    val voteId = params.getAs[Int]("slug").get
    val vote = voteRepo.getVoteThread(voteId)
    val voteOptions = voteRepo.getVoteOptionsToTuples(voteId)
    val voteResult = voteRepo.getVoteGrpResult(voteId)
    vote match {
      case Some(v) => {
        ssp("/pages/vote/viewresult", "title" -> "Show Vote Result ", "voteName" -> v.name, "description" -> v.description, "voteId" -> v.voteId, "voteMethod" -> v.voteMethod, "voteOptions" -> voteOptions, "voteResult" -> voteResult)
      }
      case _ => { "file not found" }
    }

  }

  post("/vote/update") {

    val voteName = params("voteName")
    val description = params("description")
    val voteMethod = params("voteMethod").toInt

    val sizeOfVoteOptions = params("sizeOfVoteOptions").toInt

    // val optionIds: Seq[String] = multiParams("optionId")
    //  println("option ids " + optionIds)

    params.getAs[Int]("voteId") match {
      case Some(voteId) => {
        try {
          voteRepo.updateVoteThread(voteName, description, voteMethod, voteId)
          for (seq <- (1 to sizeOfVoteOptions)) {
            params.getAs[Int]("optionId-" + seq) match {
              case Some(optionId) => {
                val option = params("option-" + seq)
                val optionDesc = params("optionDesc-" + seq)
                val id = optionId.toInt
                voteRepo.updateVoteOpton(voteId, option, optionDesc, id)
              }
              
              case _ =>{
                " Can not get a valid option id"
              }
            }
          }
          CacheMgr.voteThreadCache.remove(voteId)
          val message = "Successful Update " + voteId + " !"
          val voteOptions = voteRepo.getVoteOptionsToTuples(voteId)
          ssp("/pages/vote/view", "title" -> "Show Vote detail ", "voteName" -> voteName, "description" -> description, "voteId" -> voteId, "voteMethod" -> voteMethod, "voteOptions" -> voteOptions, "message" -> message)
        } catch {
          case e: Throwable =>
            "Have exception to update -> " + e.getMessage()
        }
      }
      case _ => {
        " Can not get a valid vote Id"
      }
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