package org.dragonstudio.wechat

import org.dragonstudio.wechat.util._

object wechat {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  WechatUtils.createMenu                          //> {"access_token":"XmFuAHgmpy5jPiIF8a_zoEJvj5kkNXkDvqKxkq53F1rxvUAKAl6r5R-p3KS
                                                  //| ZAS5q5Ev3cL1a2y-YWALb18JRZRSbGj898M97zKkUVaDSINE-QOPTxiaPyryw1xRJf3PVNkbOg4s
                                                  //| cayzd30iMPdMnLQ","expires_in":7200}
                                                  //| accessToken "XmFuAHgmpy5jPiIF8a_zoEJvj5kkNXkDvqKxkq53F1rxvUAKAl6r5R-p3KSZAS5
                                                  //| q5Ev3cL1a2y-YWALb18JRZRSbGj898M97zKkUVaDSINE-QOPTxiaPyryw1xRJf3PVNkbOg4scayz
                                                  //| d30iMPdMnLQ"
                                                  //|  access_token -> "XmFuAHgmpy5jPiIF8a_zoEJvj5kkNXkDvqKxkq53F1rxvUAKAl6r5R-p3K
                                                  //| SZAS5q5Ev3cL1a2y-YWALb18JRZRSbGj898M97zKkUVaDSINE-QOPTxiaPyryw1xRJf3PVNkbOg4
                                                  //| scayzd30iMPdMnLQ"
                                                  //|  will post to https://api.weixin.qq.com/cgi-bin/menu/create?access_token="Xm
                                                  //| FuAHgmpy5jPiIF8a_zoEJvj5kkNXkDvqKxkq53F1rxvUAKAl6r5R-p3KSZAS5q5Ev3cL1a2y-YWA
                                                  //| Lb18JRZRSbGj898M97zKkUVaDSINE-QOPTxiaPyryw1xRJf3PVNkbOg4scayzd30iMPdMnLQ"
}