package wechat7.req

class LocationMessage(ToUserName: String, FromUserName: String, CreateTime: Long, MsgType: String, val Location_X: String, val Location_Y: String, val Scale: String, val Label: String)
  extends BaseMessage(ToUserName, FromUserName, CreateTime, MsgType) {

}