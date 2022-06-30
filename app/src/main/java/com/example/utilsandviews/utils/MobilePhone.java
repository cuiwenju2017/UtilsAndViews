package com.example.utilsandviews.utils;

import android.text.TextUtils;

//验证手机号格式工具
public class MobilePhone {

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         *"[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,
         *[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    //8位数字加字母(包含大写)验证码验证
    public static boolean isCode(String codeNum) {
        String codeV = "^[A-Za-z0-9]{8}+$";
        if (TextUtils.isEmpty(codeNum))
            return false;
        else
            return codeNum.matches(codeV);
    }

    //3-16位用户名数字加字母(包含大写)验证
    public static boolean isUserName(String userName) {
        String userNameV = "^[a-z0-9_A-Z]{3,16}$";
        if (TextUtils.isEmpty(userName))
            return false;
        else return userName.matches(userNameV);
    }

    //6-18位数字加字母(不包含大写)密码验证
    public static boolean isPassWord(String passWord) {
        String passWordV = "^[a-z0-9_-]{6,18}$";
        if (TextUtils.isEmpty(passWord))
            return false;
        else return passWord.matches(passWordV);
    }

    //8-16位数字、字母、特殊符号组合正则验证
    public static boolean isPassWordZuhe(String passWord) {
        String passWordV = "^(?=(.*[A-Za-z]))(?=(.*\\d))(?=(.*[ !\"#$%&'()*+,-./<=>?@\\[\\]^_`{|}~:;·\\\\£€¥]))^.{8,16}$";
        if (TextUtils.isEmpty(passWord))
            return false;
        else return passWord.matches(passWordV);
    }

    //8-16位数字、大小写字母、特殊符号组合正则验证
    public static boolean isPassWordZuheDaxiaoxie(String passWord) {
        String passWordV = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}$";
        if (TextUtils.isEmpty(passWord))
            return false;
        else return passWord.matches(passWordV);
    }

    //电子邮箱验证
    public static boolean isEmail(String email) {
        String emailV = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if (TextUtils.isEmpty(email))
            return false;
        else return email.matches(emailV);
    }

    //url链接验证
    public static boolean isUrl(String url) {
        String urlV = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
        if (TextUtils.isEmpty(url))
            return false;
        else return url.matches(urlV);
    }

    //IP地址验证
    public static boolean isIP(String ip) {
        String ipV = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        if (TextUtils.isEmpty(ip))
            return false;
        else return ip.matches(ipV);
    }

    //中文字符验证
    public static boolean isCN(String cn) {
        String cnV = "[\\u4e00-\\u9fa5]";
        if (TextUtils.isEmpty(cn))
            return false;
        else return cn.matches(cnV);
    }
}
