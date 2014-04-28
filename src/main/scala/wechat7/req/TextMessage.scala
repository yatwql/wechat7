package wechat7.req



class TextMessage(ToUserName:String, FromUserName:String,CreateTime:Long,MsgType:String,val Content:String) extends BaseMessage(ToUserName, FromUserName,CreateTime,MsgType) {
 //var =""
}