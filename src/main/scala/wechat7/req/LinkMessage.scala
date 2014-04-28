package wechat7.req

class LinkMessage(ToUserName: String, FromUserName: String, CreateTime: Long, MsgType: String, val Title: String, val Description: String, val url: String)
  extends BaseMessage(ToUserName, FromUserName, CreateTime, MsgType) {

}