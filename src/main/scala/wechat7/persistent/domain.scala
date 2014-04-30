package wechat7.persistent

import scala.slick.driver.JdbcProfile
import java.sql.Date
//define driver
trait Profile {
val profile: JdbcProfile
}
 
trait DomainComponent { this: Profile =>
import profile.simple._
 
case class AppUser(name: String, email: String, fullName:String, password: String, role:String,id: Int = 0)
class AppUsers(tag: Tag) extends Table[AppUser](tag, "app_users") {
def id = column[Int]("id", O.PrimaryKey , O.AutoInc)
def name = column[String]("name", O.NotNull, O.DBType("VARCHAR(100)"))
def fullName = column[String]("fullname", O.NotNull, O.DBType("VARCHAR(100)"))
def email = column[String]("email", O.NotNull, O.DBType("VARCHAR(100)"))
def password = column[String]("password", O.NotNull, O DBType ("VARCHAR(100)"))
def role = column[String]("role", O.NotNull, O.DBType("VARCHAR(100)"))
def * = (name, email, fullName, password,role, id) <> (AppUser.tupled, AppUser.unapply)
}
val appUsers= TableQuery[AppUsers]

case class Article(title: String, description: String, picUrl: String, url: String, id: Int = 0)
class Articles(tag: Tag) extends Table[Article](tag, "articles") {
def id = column[Int]("id", O.PrimaryKey , O.AutoInc)
def title = column[String]("title", O.NotNull, O.DBType("VARCHAR(100)"))
def description = column[String]("description", O.NotNull, O.DBType("VARCHAR(1000)"))
def picUrl = column[String]("picUrl", O.NotNull, O DBType ("VARCHAR(300)"))
def url = column[String]("url", O.NotNull, O DBType ("VARCHAR(300)"))
def * = (title, description, picUrl, url, id) <> (Article.tupled, Article.unapply)
}
val articles= TableQuery[Articles]

case class VoteTopic(name:String,description:String, id: Int = 0)
class VoteTopics(tag: Tag) extends Table[VoteTopic](tag, "vote_topics") {
  def id = column[Int]("id", O.PrimaryKey , O.AutoInc)
  def name = column[String]("name", O.NotNull, O.DBType("VARCHAR(100)"))
  def description = column[String]("description", O.NotNull, O.DBType("VARCHAR(1000)"))
  def * = (name, description, id) <> (VoteTopic.tupled, VoteTopic.unapply)
}
val voteTopics= TableQuery[VoteTopics]

case class VoteResult(fromUser:String,voteId:Int,fromLocationX:String="", fromLocationY:String="", id: Int = 0)
class VoteResults(tag: Tag) extends Table[VoteResult](tag, "vote_results") {
  def id = column[Int]("id", O.PrimaryKey , O.AutoInc)
  def fromUser = column[String]("fromUser", O.NotNull, O.DBType("VARCHAR(100)"))
  def voteId = column[Int]("voteId", O.NotNull)
  def fromLocationX = column[String]("fromLocationX", O.Default(""), O.DBType("VARCHAR(100)"))
  def fromLocationY = column[String]("fromLocationY", O.Default(""), O.DBType("VARCHAR(100)"))
  def * = (fromUser, voteId, fromLocationX,fromLocationY,id) <> (VoteResult.tupled, VoteResult.unapply)
}
val voteResults= TableQuery[VoteResults]

case class Message(fromUser:String,toUser:String,msgType:String, content:String,creationTime:java.sql.Timestamp=new java.sql.Timestamp(new java.util.Date().getTime()),id: Int =0)
class Messages(tag: Tag) extends Table[Message](tag, "messages") {
  def id = column[Int]("id", O.PrimaryKey , O.AutoInc)
  def fromUser = column[String]("fromUser", O.NotNull, O.DBType("VARCHAR(100)"))
  def toUser = column[String]("toUser", O.NotNull, O.DBType("VARCHAR(100)"))
  def msgType = column[String]("msgType", O.NotNull, O.DBType("VARCHAR(20)"))
  def content = column[String]("content", O.Default(""), O.DBType("VARCHAR(1000)"))
  def creationTime = column[java.sql.Timestamp]("creationTime", O.Default(new java.sql.Timestamp(new java.util.Date().getTime())), O.DBType("Timestamp"))
  def * = (fromUser, toUser, msgType,content,creationTime,id) <> (Message.tupled, Message.unapply)
}

val messages= TableQuery[Messages]
}

