package wechat7

import wechat7.util._

object wechat {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  WechatUtils.createMenu                          //> The return message -> {"access_token":"9ofS3QMg7o_23zLTHqseettgXeHLnuISeJyXY
                                                  //| DAfdms1KX7tVBwif2bTB5Ozd2dxy7tM5ABw9kn_xExkcipWQQranGHsoDty5YbctI9hDudGdPlvW
                                                  //| g2nJnh6wKZGLwcS6or8tLDH8p6MnvILegzu0Q","expires_in":7200}
                                                  //| The accessToken -> 9ofS3QMg7o_23zLTHqseettgXeHLnuISeJyXYDAfdms1KX7tVBwif2bTB
                                                  //| 5Ozd2dxy7tM5ABw9kn_xExkcipWQQranGHsoDty5YbctI9hDudGdPlvWg2nJnh6wKZGLwcS6or8t
                                                  //| LDH8p6MnvILegzu0Q
                                                  //|  access_token -> 9ofS3QMg7o_23zLTHqseettgXeHLnuISeJyXYDAfdms1KX7tVBwif2bTB5O
                                                  //| zd2dxy7tM5ABw9kn_xExkcipWQQranGHsoDty5YbctI9hDudGdPlvWg2nJnh6wKZGLwcS6or8tLD
                                                  //| H8p6MnvILegzu0Q
                                                  //|  will post to https://api.weixin.qq.com/cgi-bin/menu/create?access_token=9of
                                                  //| S3QMg7o_23zLTHqseettgXeHLnuISeJyXYDAfdms1KX7tVBwif2bTB5Ozd2dxy7tM5ABw9kn_xEx
                                                  //| kcipWQQranGHsoDty5YbctI9hDudGdPlvWg2nJnh6wKZGLwcS6or8tLDH8p6MnvILegzu0Q
                                                  
}