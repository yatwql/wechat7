package wechat7.util

import scala.xml.Node

import spray.caching.Cache
import spray.caching.LruCache
import scala.concurrent.duration._
object CacheMgr {
  val nicknameCache: Cache[Option[String]] = LruCache(maxCapacity = 500,initialCapacity=100,timeToLive=Duration(24,HOURS),timeToIdle=Duration(12,HOURS))
  val userActionCache: Cache[Option[String]] = LruCache(maxCapacity = 500,initialCapacity=100,timeToLive=Duration(30,MINUTES),timeToIdle=Duration(20,MINUTES))
  val nextActionCache: Cache[Option[String]] = LruCache(maxCapacity = 64,initialCapacity=32,timeToLive=Duration(2,HOURS),timeToIdle=Duration(1,HOURS))
  val currentActionCache: Cache[Option[String]] = LruCache(maxCapacity = 64,initialCapacity=32,timeToLive=Duration(2,HOURS),timeToIdle=Duration(1,HOURS))
  val articleCache: Cache[Seq[Node]] = LruCache(maxCapacity = 64,initialCapacity=32,timeToLive=Duration(2,HOURS),timeToIdle=Duration(1,HOURS))
  val voteThreadCache: Cache[Option[(String, String, Int, Seq[String])]] = LruCache(maxCapacity = 64,initialCapacity=32,timeToLive=Duration(2,HOURS),timeToIdle=Duration(1,HOURS))
}