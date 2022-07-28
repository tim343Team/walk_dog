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
    //训练记录
    public static String getTrainingPage(){
        return host + "/userInfo/getTrainingPage";
    }
    //遛狗总记录
    public static String getWalkDogLogCount() {
        return host + "/userInfo/getWalkDogLogCount";
    }
    //遛狗详细记录
    public static String getWalkDogLog() {
        return host + "/userInfo/getWalkDogLog";
    }
    //
    public static String getTogetherPage(){
        return host + "/userInfo/getTogetherPage";
    }

    public static String getFriendList(){
        return host + "/friend/FriendList";
    }

    //拒绝好友
    public static String getDeclineTheInvitation(){
        return host + "/friend/declineTheInvitation";
    }

    //同意好友
    public static String getAcceptTheInvitation(){
        return host + "/friend/acceptTheInvitation";
    }

    public static String getInviteFriendList(){
        return host + "/friend/invitedOtherList";
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

    //使用狗狗
    public static String useDogUrl() {
        return host + "/dog/useDog";
    }

    //移除使用中的狗狗
    public static String removeDogUrl() {
        return host + "/dog/removeDog";
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

    //遛狗坐标传入
    public static String addCoordUrl() {
        return host + "/index/addCoord";
    }

    //获取我的道具
    public static String getUserPropUrl() {
        return host + "/prop/getUserProp";
    }

    //获取我的道具
    public static String getDogPropUrl() {
        return host + "/dog/getDogProp";
    }

    //移除使用中的道具
    public static String getremovePropUrl() {
        return host + "/prop/removeProp";
    }

    //装备道具
    public static String getusePropUrl() {
        return host + "/prop/useProp";
    }

    //根据id获取道具详情
    public static String getPropInfoUrl() {
        return host + "/prop/getPropInfo";
    }

    //使用狗粮
    public static String useDogFoodUrl() {
        return host + "/prop/useDogFood";
    }

    //售卖道具
    public static String sellPropUrl() {
        return host + "/shopping/sellProp";
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

    //购买狗狗
    public static String buyDogUrl() {
        return host + "/shopping/buyDog";
    }

    //购买道具
    public static String buyPropUrl() {
        return host + "/shopping/buyProp";
    }

    //取消售卖狗狗
    public static String cancelSellDogUrl() {
        return host + "/shopping/cancelSellDog";
    }

    //取消售卖道具
    public static String cancelSellPropUrl() {
        return host + "/shopping/cancelSellProp";
    }

    //获取所有训练项目
    public static String getAllTrainUrl() {
        return host + "/trainDog/getAllTrain";
    }

    //训练狗狗
    public static String trainDogUrl() {
        return host + "/trainDog/trainDog";
    }

    //升级
    public static String upDogLevelUrl() {
        return host + "/dog/upDogLevel";
    }

    //获取商城售卖狗粮详情
    public static String getShopDogFoodUrl() {
        return host + "/userInfo/getShopDogFood";
    }

    //好友列表
    public static String getFriendListUrl() {
        return host + "/friend/FriendList";
    }

    //发送好友邀请
    public static String sendFriendInvitedUrl() {
        return host + "/friend/sendFriendInvited";
    }

    //好友狗狗详情
    public static String friendDogDetailUrl() {
        return host + "/friend/friendDogDetail";
    }

    //设置好友备注
    public static String setNoteUrl() {
        return host + "/friend/setNote";
    }

    //移除好友
    public static String delFriendUrl() {
        return host + "/friend/delFriend";
    }

    //狗狗商城列表
    public static String dogListUrl() {
        return host + "/shopping/dogList";
    }

    //道具商城列表
    public static String propListUrl() {
        return host + "/shopping/propList";
    }

}
