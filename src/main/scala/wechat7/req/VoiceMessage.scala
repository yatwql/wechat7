package wechat7.req

class VoiceMessage(ToUserName: String, FromUserName: String, CreateTime: Long, MsgType: String, val MediaId: String, val Format: String)
  extends BaseMessage(ToUserName, FromUserName, CreateTime, MsgType) {

}