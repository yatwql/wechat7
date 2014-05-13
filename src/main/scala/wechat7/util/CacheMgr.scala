package wechat7.util

import scala.xml.Node

import spray.caching.Cache
import spray.caching.LruCache
object CacheMgr {
  val nicknameCache: Cache[Option[String]] = LruCache(maxCapacity = 500)
  val userActionCache: Cache[Option[String]] = LruCache(maxCapacity = 500)
  val nextActionCache: Cache[Option[String]] = LruCache(maxCapacity = 100)
  val currentActionCache: Cache[Option[String]] = LruCache(maxCapacity = 100)
  val articleCache: Cache[Seq[Node]] = LruCache(maxCapacity = 100)
  val voteThreadCache: Cache[Option[(String, String, Int, Seq[String])]] = LruCache(maxCapacity = 100)
}