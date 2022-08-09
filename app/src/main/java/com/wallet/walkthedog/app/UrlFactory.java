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

    public static String getCheckEmailUrl() {
        return host + "/userInfo/checkEmail";
    }

    public static String getUpdateEmailUrl() {
        return host + "/userInfo/updateEmail";
    }

    //训练记录
    public static String getTrainingPage() {
        return host + "/userInfo/getTrainingPage";
    }

    public static String getSysDataCode() {
        return host + "/sysData/getSysDataCode";
    }

    public static String tokenAudit() {
        return host + "/userInfo/tokenAudit";
    }

    //遛狗总记录
    public static String getWalkDogLogCount() {
        return host + "/userInfo/getWalkDogLogCount";
    }

    public static String coinList() {
        return host + "/coin/all";
    }

    public static String countryList() {
        return host + "/advertise/getAllCountry";
    }

    public static String AdpageByUnit() {
        return host + "/advertise/page-by-unit";
    }

    public static String orderBuy() {
        return host + "/order/buy";
    }
    public static String orderSell() {
        return host + "/order/sell";
    }

    public static String orderDetail() {
        return host + "/order/detail";
    }

    public static String orderCancel() {
        return host + "/order/cancel";
    }
    public static String releaseOrder(){
        return host +"/order/release";
    }

    public static String orderPay() {
        return host + "/order/pay";
    }

    //遛狗详细记录
    public static String getWalkDogLog() {
        return host + "/userInfo/getWalkDogLog";
    }

    public static String getWallet() {
        return host + "/wallet/getWallet";
    }

    public static String sellDogFood() {
        return host + "/userInfo/sellDogFood";
    }

    public static String userinfo() {
        return host + "/userInfo/getUserInfo";
    }

    public static String updateNickName() {
        return host + "/userInfo/updateNickName";
    }

    public static String getDogFoodCat() {
        return host + "/userInfo/getDogFoodCat";
    }

    public static String getTokenLog() {
        return host + "/userInfo/getTokenLog";
    }

    public static String getDogFoodLog() {
        return host + "/userInfo/getDogFoodLog";
    }

    //
    public static String getTogetherPage() {
        return host + "/together/getTogetherPage";
    }

    public static String getIdeaTogether() {
        return host + "/together/ideaTogether";
    }

    public static String getFriendList() {
        return host + "/friend/FriendList";
    }

    //拒绝好友
    public static String getDeclineTheInvitation() {
        return host + "/friend/declineTheInvitation";
    }

    //同意好友
    public static String getAcceptTheInvitation() {
        return host + "/friend/acceptTheInvitation";
    }

    public static String getInviteFriendList() {
        return host + "/friend/invitedOtherList";
    }

    public static String getInviteRegisterList() {
        return host + "/userInfo/getInvitePage";
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

    //获取遛狗奖励记录
    public static String getAwardPageUrl() {
        return host + "/userInfo/getAwardPage";
    }

    //领取遛狗奖励记录
    public static String getAwardUrl() {
        return host + "/userInfo/getAward";
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

    //根据code查询
    public static String getSysDataCodeUrl() {
        return host + "/sysData/getSysDataCode";
    }

    //开启宝箱
    public static String openBoxUrl() {
        return host + "/prop/openBox";
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

    //购买商城出售的狗粮
    public static String shopDogFoodUrl() {
        return host + "/userInfo/shopDogFood";
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

    //发起遛狗邀请
    public static String addTogetherUrl() {
        return host + "/together/addTogether";
    }

    //获取最新遛狗邀请通知
    public static String getNewTogethersUrl() {
        return host + "/together/getNewTogethers";
    }

    //1同意 3拒绝 4取消邀请一起遛狗
    public static String ideaTogetherUrl() {
        return host + "/together/ideaTogether";
    }

    //狗狗商城列表
    public static String dogListUrl() {
        return host + "/shopping/dogList";
    }

    //道具商城列表
    public static String propListUrl() {
        return host + "/shopping/propList";
    }

    //售卖狗狗
    public static String sellDogUrl() {
        return host + "/shopping/sellDog";
    }

    //购买出售记录
    public static String getShoppLogUrl() {
        return host + "/shopping/getShoppLog";
    }

}
