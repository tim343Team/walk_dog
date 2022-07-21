package com.wallet.walkthedog.app;


import com.wallet.walkthedog.config.AppConfig;

public class UrlFactory {
    public static final String host = AppConfig.BASE_URL;

    //发送验证码
    public static String getSendMailboxUrl() {
        return host + "/user/getEmailCheckCode.json";
    }

    //邮箱注册
    public static String getEmailRegisterUrl() {
        return host + "/user/emailRegister.json";
    }

    public static String getCheckEmailUrl(){
        return host + "/userInfo/checkEmail";
    }

    public static String getUpdateEmailUrl(){
        return host + "/userInfo/updateEmail";
    }

    //邮箱登录
    public static String getEmailLoginUrl() {
        return host + "/user/emailCheckCode.json";
    }

    //密碼登录
    public static String getPasswordLoginUrl() {
        return host + "/user/login.json";
    }

    //获取我的狗狗列表
    public static String getDogInfoUrl() {
        return host + "/dog/getDogInfo";
    }

    //获取我的狗狗列表
    public static String getUserDogUrl() {
        return host + "/dog/getUserDog";
    }

    //获取一起遛狗的好友
    public static String getWalkTheDogFriendUrl() {
        return host + "/index/getWalkTheDogFriend";
    }

    //开始遛狗
    public static String getStartWalkUrl() {
        return host + "/index/startWalkTheDog";
    }

    //停止遛狗
    public static String getStopWalkUrl() {
        return host + "/index/endWalkTheDog";
    }

    //获取我的道具
    public static String getUserPropUrl() {
        return host + "/prop/getUserProp";
    }

    //移除使用中的道具
    public static String getremovePropUrl() {
        return host + "/prop/removeProp";
    }

    //装备道具
    public static String getusePropUrl() {
        return host + "/prop/useProp";
    }

    //查询喂食消耗
    public static String getFeedDogUrl() {
        return host + "/dog/getFeedDog";
    }

    //喂食
    public static String feedDogUrl() {
        return host + "/dog/feedDog";
    }

    //获取钱包余额
    public static String getWalletUrl() {
        return host + "/wallet/getWallet";
    }

}
