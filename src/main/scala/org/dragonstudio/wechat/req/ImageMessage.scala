package org.dragonstudio.wechat.req

class ImageMessage (ToUserName:String, FromUserName:String,CreateTime:Long,MsgType:String,val picUrl:String) extends BaseMessage(ToUserName, FromUserName,CreateTime,MsgType) {
 
}