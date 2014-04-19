package org.dragonstudio.wechat

import org.scalatra._
import scala.xml._
import sun.misc.BASE64Encoder
import scalate.ScalateSupport

import atmosphere._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }
import org.json4s._
import JsonDSL._
import java.util.Date
import java.text.SimpleDateFormat
import org.fusesource.scalate.Template

import scala.concurrent._
import ExecutionContext.Implicits.global

class WechatController extends WechatAppStack {
  val TOKEN = "WANGQL"

  get("/") {
    contentType = "text/html"
    ssp("/pages/index")
  }

  get("/chat") {
    contentType = "text/html"
    ssp("/pages/chat")
  }

  get("/wechatauth") {
    contentType = "text/html"
    val result = checkSignature()
    println(result)

  }

  post("/wechatauth") {
    contentType = "xml"
    println("request body is -> " + request.body)
    //request.body

    val wxl = XML.loadString(request.body)
    val toUser = (wxl \ "ToUserName").text

    println("toUser is " + toUser)

    val fromUser = (wxl \ "FromUserName").text

    println("FromUserName is " + fromUser)

    val content = (wxl \ "Content").text
    println("content is " + content)

    <xml>
      <ToUserName><![CDATA[oIySzjiralAU2LFHbklct2m0Mdcw]]></ToUserName>
      <FromUserName><![CDATA[gh_c2bb951675bb]]></FromUserName>
      <CreateTime>12345678</CreateTime>
      <MsgType><![CDATA[text]]></MsgType>
      <Content><![CDATA[你好]]></Content>
    </xml>

  }

  get("/pages/:slug") {
    contentType = "text/html"
    PageDao.pages find (_.slug == params("slug")) match {
      case Some(page) => ssp("/pages/show", "page" -> page)
      case None => halt(404, "Can not locate the page - " + params("slug"))
    }
  }

  atmosphere("/the-chat") {
    new AtmosphereClient {
      def receive: AtmoReceive = {
        case Connected =>
          println("Client %s is connected" format uuid)
          broadcast(("author" -> "Someone") ~ ("message" -> "joined the room") ~ ("time" -> (new Date().getTime.toString)), Everyone)

        case Disconnected(ClientDisconnected, _) =>
          broadcast(("author" -> "Someone") ~ ("message" -> "has left the room") ~ ("time" -> (new Date().getTime.toString)), Everyone)

        case Disconnected(ServerDisconnected, _) =>
          println("Server disconnected the client %s" format uuid)
        case _: TextMessage =>
          send(("author" -> "system") ~ ("message" -> "Only json is allowed") ~ ("time" -> (new Date().getTime.toString)))

        case JsonMessage(json) =>
          println("Got message %s from %s".format((json \ "message").extract[String], (json \ "author").extract[String]))
          val msg = json merge (("time" -> (new Date().getTime().toString)): JValue)
          broadcast(msg) // by default a broadcast is to everyone but self
        //  send(msg) // also send to the sender
      }
    }
  }

  error {
    case t: Throwable => t.printStackTrace()
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

  def checkSignature(): String =
    {
      val signature = params.getOrElse("signature", "")
      println("signature = " + signature)
      val timestamp = params.getOrElse("timestamp", "")
      println("timestamp = " + timestamp)
      val nonce = params.getOrElse("nonce", "")
      println("nonce = " + nonce)
      val echostr = params.getOrElse("echostr", "")
      println("echostr = " + echostr)

      val token = TOKEN
      println("token = " + token)
      val tmpStr = Array(token, timestamp, nonce).sortWith(_ < _).mkString

      println("tmpStr = " + tmpStr)

      val md = java.security.MessageDigest.getInstance("SHA1")

      val ha = md.digest(tmpStr.getBytes("UTF-8")).map("%02x".format(_)).mkString

      println("ha = " + ha)

      println("signature = " + signature)

      if (ha == signature) {
        echostr
      } else {
        "InvalidSigatureResult"
      }

    }

}

object Hex {
  def hex2Bytes(hex: String): Array[Byte] = {

    (for { i <- 0 to hex.length - 1 by 2 if i > 0 || !hex.startsWith("0x") }

      yield hex.substring(i, i + 2))

      .map(Integer.parseInt(_, 16).toByte).toArray

  }

  def valueOf(buf: Array[Byte]): String = buf.map("%02X" format _).mkString

}

case class Page(slug: String, title: String, summary: String, body: String)

object PageDao {
  val pages = List(
    Page("bacon-ipsum",
      "Bacon ipsum dolor sit amet hamburger",
      """Shankle pancetta turkey ullamco exercitation laborum ut
        officia corned beef voluptate.""",
      """Fugiat mollit, spare ribs pork belly flank voluptate ground
        round do sunt laboris jowl. Meatloaf excepteur hamburger pork
        chop fatback drumstick frankfurter pork aliqua.
        Pork belly meatball meatloaf labore. Exercitation commodo nisi
        shank, beef drumstick duis. Venison eu shankle sunt commodo short
        loin dolore chicken prosciutto beef swine elit quis beef ribs.
        Short ribs enim shankle ribeye andouille bresaola corned beef
        jowl ut beef.Tempor do boudin, pariatur nisi biltong id elit
        dolore non sunt proident sed. Boudin consectetur jowl ut dolor
        sunt consequat tempor pork chop capicola pastrami mollit short
        loin."""),
    Page("veggie-ipsum",
      "Arugula prairie turnip desert raisin sierra leone",
      """Veggies sunt bona vobis, proinde vos postulo esse magis napa
      cabbage beetroot dandelion radicchio.""",
      """Brussels sprout mustard salad jícama grape nori chickpea
      dulse tatsoi. Maize broccoli rabe collard greens jícama wattle
      seed nori garbanzo epazote coriander mustard."""))
}

