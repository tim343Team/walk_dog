package com.wallet.walkthedog.data;

import com.wallet.walkthedog.dao.request.AwardRequest;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.CardEditRequset;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.InviteRequest;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.dao.request.MerchantRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.SellRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.dao.request.TrainRequest;

public interface DataSource {

    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }

    void checkInvitedCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback);

    void emailRegister(EmailRegisterRequest request, DataCallback dataCallback);

    void emailLogin(EmailLoginRequest request, DataCallback dataCallback);

    void passwordLogin(EmailLoginRequest request, DataCallback dataCallback);

    void getUserDog(int type,int pageNo,DataCallback dataCallback);

    void useDog(String dogId,DataCallback dataCallback);

    void removeDog(String dogId,DataCallback dataCallback);

    void useDogInfo(DataCallback dataCallback);

    void getDogInfo(String dogId,DataCallback dataCallback);

    void getUseDog(String dogId,DataCallback dataCallback);

    void getFeedDog(String dogId,DataCallback dataCallback);

    void feedDog(String dogId,DataCallback dataCallback);

    void getWallet(String type,DataCallback dataCallback);

    void buyDog(BuyRequest request, DataCallback dataCallback);

    void buyProp(BuyRequest request, DataCallback dataCallback);

    void cancelSellDog(BuyRequest request, DataCallback dataCallback);

    void cancelSellProp(BuyRequest request, DataCallback dataCallback);

    void getSysDataCode(String code, DataCallback dataCallback);

    void openBox(OpreationPropRequest request, DataCallback dataCallback);

    void getWalkTheDogFriend(DataCallback dataCallback);

    void startWalkDog(SwitchWalkRequest request,DataCallback dataCallback);

    void stopWalkDog(SwitchWalkRequest request,DataCallback dataCallback);

    void getAwardPage(AwardRequest request, DataCallback dataCallback);

    void getAward(AwardRequest request, DataCallback dataCallback);

    void addCoord(SwitchWalkRequest request,DataCallback dataCallback);

    void getUserProp(int type,int pageNo,DataCallback dataCallback);

    void getDogProp(String dogId,int pageNo,DataCallback dataCallback);

    void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback);

    void getAddProp(OpreationPropRequest request,DataCallback dataCallback);

    void getPropDetailInfo(OpreationPropRequest request,DataCallback dataCallback);

    void useDogFood(OpreationPropRequest request,DataCallback dataCallback);

    void sellProp(SellRequest request, DataCallback dataCallback);

    void getAllTrain(DataCallback dataCallback);

    void trainDog(TrainRequest request,DataCallback dataCallback);

    void upDogLevel(String dogId,DataCallback dataCallback);

    void getShopDogFood(DataCallback dataCallback);

    void ShopDogFood(int dogFoodId, int number,String passWord,DataCallback dataCallback);

    void getFriendList(int pageNo,DataCallback dataCallback);

    void friendEmail(String friendEmail,DataCallback dataCallback);

    void getFriendDogDetail(String id,DataCallback dataCallback);

    void setNote(FriendRequest request, DataCallback dataCallback);

    void delFriend(FriendRequest request, DataCallback dataCallback);

    void addTogether(InviteRequest request, DataCallback dataCallback);

    void deleteTogether(String togetherId, DataCallback dataCallback);

    void getNewTogethersUrl( DataCallback dataCallback);

    void getVersionInfo( DataCallback dataCallback);

    void ideaTogether(String togetherId, int status, DataCallback dataCallback);

    void getDogList(MailRequest request, int pageNo, DataCallback dataCallback);

    void getPropList(MailRequest request, int pageNo, DataCallback dataCallback);

    void getPropDownBox(DataCallback dataCallback);

    void getDogDownBox(DataCallback dataCallback);

    void sellDog(SellRequest request,  DataCallback dataCallback);

    void getShoppLog(int type,int pageNo,  DataCallback dataCallback);

    void getBankAccount(DataCallback dataCallback);

    void getApproveBank(CardEditRequset requset,DataCallback dataCallback);

    void getApproveSwift(CardEditRequset requset,DataCallback dataCallback);

    void applyMerchant(MerchantRequest requset, DataCallback dataCallback);

    void getApproveBusiness(DataCallback dataCallback);

    void cancleMerchant(DataCallback dataCallback);

    void merchantStatus(DataCallback dataCallback);

}
