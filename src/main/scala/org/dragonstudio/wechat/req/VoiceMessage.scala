package org.dragonstudio.wechat.req

class VoiceMessage(ToUserName: String, FromUserName: String, CreateTime: Long, MsgType: String, val MediaId: String, val Format: String)
  extends BaseMessage(ToUserName, FromUserName, CreateTime, MsgType) {

}