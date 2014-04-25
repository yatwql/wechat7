package org.dragonstudio.wechat.persistent
import scala.slick.driver.JdbcProfile
import java.sql.Date
class SlickDemo(override val profile: JdbcProfile = SlickDBDriver.getDriver) extends DomainComponent with Profile {
 import profile.simple._
 val conn = new DBConnection(profile)
 def test: Unit = {
 conn.dbObject withSession { implicit session: Session =>
 // create  table  selected environment
 try {
    
   //appUsers.ddl.drop
 appUsers.ddl.create
  articles.ddl.create
  voteTopics.ddl.create
  voteResults.ddl.create
 } catch {
 case ex: Exception => println(ex.getMessage)
 }
 
 // insert AppUser into database
 appUsers.insert(AppUser("stallman","Stallman","stallman.wang@foxmail.com", "stallman","admin"))
  appUsers.insert(AppUser("yatwql","joe","yatwql@qq.com", "yatwql","admin"))
  appUsers.insert(AppUser("test","joe","yatwql@qq.com", "test","admin"))
 println("======================retrieve from database ====================")
 appUsers.list foreach println
 // delete
 val query = for { emp <- appUsers if (emp.name === "test") } yield emp
 query.delete
 println("======================retrieve after delete ====================")
 appUsers.list foreach println
 }
 }
 }
 object SlickDemoApp extends App {
 (new SlickDemo).test
 }
