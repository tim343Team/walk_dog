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

public class DataRepository implements DataSource {
    private static DataRepository INSTANCE = null;
    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;
    private boolean isLocal = false;

    private DataRepository(DataSource mRemoteDataSource, DataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static DataRepository getInstance(DataSource mRemoteDataSource, DataSource mLocalDataSource) {
        if (INSTANCE == null) INSTANCE = new DataRepository(mRemoteDataSource, mLocalDataSource);
        return INSTANCE;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public void checkInvitedCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.checkInvitedCode(request, dataCallback);
        else mRemoteDataSource.checkInvitedCode(request, dataCallback);
    }

    @Override
    public void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.sendMailboxCode(request, dataCallback);
        else mRemoteDataSource.sendMailboxCode(request, dataCallback);
    }

    @Override
    public void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailCheckCode(request, dataCallback);
        else mRemoteDataSource.emailCheckCode(request, dataCallback);
    }

    @Override
    public void emailRegister(EmailRegisterRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailRegister(request, dataCallback);
        else mRemoteDataSource.emailRegister(request, dataCallback);
    }

    @Override
    public void emailLogin(EmailLoginRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.emailLogin(request, dataCallback);
        else mRemoteDataSource.emailLogin(request, dataCallback);
    }

    @Override
    public void passwordLogin(EmailLoginRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.passwordLogin(request, dataCallback);
        else mRemoteDataSource.passwordLogin(request, dataCallback);
    }

    @Override
    public void getUserDog(int type,int pageNo,DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUserDog(type,pageNo,dataCallback);
        else mRemoteDataSource.getUserDog(type,pageNo,dataCallback);
    }

    @Override
    public void useDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.useDog(dogId, dataCallback);
        else mRemoteDataSource.useDog(dogId, dataCallback);
    }

    @Override
    public void removeDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.removeDog(dogId, dataCallback);
        else mRemoteDataSource.removeDog(dogId, dataCallback);
    }

    @Override
    public void getDogInfo(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getDogInfo(dogId, dataCallback);
        else mRemoteDataSource.getDogInfo(dogId, dataCallback);
    }

    @Override
    public void getUseDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUseDog(dogId, dataCallback);
        else mRemoteDataSource.getUseDog(dogId, dataCallback);
    }

    @Override
    public void getFeedDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getFeedDog(dogId, dataCallback);
        else mRemoteDataSource.getFeedDog(dogId, dataCallback);
    }

    @Override
    public void feedDog(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.feedDog(dogId, dataCallback);
        else mRemoteDataSource.feedDog(dogId, dataCallback);
    }

    @Override
    public void getWallet(String type, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getWallet(type, dataCallback);
        else mRemoteDataSource.getWallet(type, dataCallback);
    }

    @Override
    public void buyDog(BuyRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.buyDog(request, dataCallback);
        else mRemoteDataSource.buyDog(request, dataCallback);
    }

    @Override
    public void buyProp(BuyRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.buyProp(request, dataCallback);
        else mRemoteDataSource.buyProp(request, dataCallback);
    }

    @Override
    public void cancelSellDog(BuyRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.cancelSellDog(request, dataCallback);
        else mRemoteDataSource.cancelSellDog(request, dataCallback);
    }

    @Override
    public void cancelSellProp(BuyRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.cancelSellProp(request, dataCallback);
        else mRemoteDataSource.cancelSellProp(request, dataCallback);
    }

    @Override
    public void getSysDataCode(String code, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getSysDataCode(code, dataCallback);
        else mRemoteDataSource.getSysDataCode(code, dataCallback);
    }

    @Override
    public void openBox(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.openBox(request, dataCallback);
        else mRemoteDataSource.openBox(request, dataCallback);
    }

    @Override
    public void getWalkTheDogFriend(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getWalkTheDogFriend(dataCallback);
        else mRemoteDataSource.getWalkTheDogFriend(dataCallback);
    }

    @Override
    public void startWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.startWalkDog(request, dataCallback);
        else mRemoteDataSource.startWalkDog(request, dataCallback);
    }

    @Override
    public void stopWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.stopWalkDog(request, dataCallback);
        else mRemoteDataSource.stopWalkDog(request, dataCallback);
    }

    @Override
    public void getAwardPage(AwardRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAwardPage(request, dataCallback);
        else mRemoteDataSource.getAwardPage(request, dataCallback);
    }

    @Override
    public void getAward(AwardRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAward(request, dataCallback);
        else mRemoteDataSource.getAward(request, dataCallback);
    }

    @Override
    public void addCoord(SwitchWalkRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.addCoord(request, dataCallback);
        else mRemoteDataSource.addCoord(request, dataCallback);
    }

    @Override
    public void getUserProp(int type,int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getUserProp(type,pageNo, dataCallback);
        else mRemoteDataSource.getUserProp(type,pageNo, dataCallback);
    }

    @Override
    public void getDogProp(String dogId, int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getDogProp(dogId, pageNo, dataCallback);
        else mRemoteDataSource.getDogProp(dogId, pageNo, dataCallback);
    }

    @Override
    public void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getRemoveProp(request, dataCallback);
        else mRemoteDataSource.getRemoveProp(request, dataCallback);
    }

    @Override
    public void getAddProp(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAddProp(request, dataCallback);
        else mRemoteDataSource.getAddProp(request, dataCallback);
    }

    @Override
    public void getPropDetailInfo(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getPropDetailInfo(request, dataCallback);
        else mRemoteDataSource.getPropDetailInfo(request, dataCallback);
    }

    @Override
    public void useDogFood(OpreationPropRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.useDogFood(request, dataCallback);
        else mRemoteDataSource.useDogFood(request, dataCallback);
    }

    @Override
    public void sellProp(SellRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.sellProp(request, dataCallback);
        else mRemoteDataSource.sellProp(request, dataCallback);
    }

    @Override
    public void getAllTrain(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getAllTrain(dataCallback);
        else mRemoteDataSource.getAllTrain(dataCallback);
    }

    @Override
    public void trainDog(TrainRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.trainDog(request, dataCallback);
        else mRemoteDataSource.trainDog(request, dataCallback);
    }

    @Override
    public void upDogLevel(String dogId, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.upDogLevel(dogId, dataCallback);
        else mRemoteDataSource.upDogLevel(dogId, dataCallback);
    }

    @Override
    public void getShopDogFood(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getShopDogFood( dataCallback);
        else mRemoteDataSource.getShopDogFood( dataCallback);
    }

    @Override
    public void ShopDogFood(int dogFoodId, int number, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.ShopDogFood(dogFoodId,number, dataCallback);
        else mRemoteDataSource.ShopDogFood(dogFoodId,number, dataCallback);
    }

    @Override
    public void getFriendList(int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getFriendList(pageNo, dataCallback);
        else mRemoteDataSource.getFriendList(pageNo, dataCallback);
    }

    @Override
    public void friendEmail(String friendEmail, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.friendEmail(friendEmail, dataCallback);
        else mRemoteDataSource.friendEmail(friendEmail, dataCallback);
    }

    @Override
    public void getFriendDogDetail(String id, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getFriendDogDetail(id, dataCallback);
        else mRemoteDataSource.getFriendDogDetail(id, dataCallback);
    }

    @Override
    public void setNote(FriendRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.setNote(request, dataCallback);
        else mRemoteDataSource.setNote(request, dataCallback);
    }

    @Override
    public void delFriend(FriendRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.delFriend(request, dataCallback);
        else mRemoteDataSource.delFriend(request, dataCallback);
    }

    @Override
    public void addTogether(InviteRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.addTogether(request, dataCallback);
        else mRemoteDataSource.addTogether(request, dataCallback);
    }

    @Override
    public void getNewTogethersUrl(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getNewTogethersUrl( dataCallback);
        else mRemoteDataSource.getNewTogethersUrl( dataCallback);
    }

    @Override
    public void ideaTogether(String togetherId, int status, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.ideaTogether(togetherId,status, dataCallback);
        else mRemoteDataSource.ideaTogether(togetherId,status, dataCallback);
    }

    @Override
    public void getDogList(MailRequest request, int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getDogList(request, pageNo, dataCallback);
        else mRemoteDataSource.getDogList(request, pageNo, dataCallback);
    }

    @Override
    public void getPropList(MailRequest request, int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getPropList(request, pageNo, dataCallback);
        else mRemoteDataSource.getPropList(request, pageNo, dataCallback);
    }

    @Override
    public void sellDog(SellRequest request, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.sellDog(request, dataCallback);
        else mRemoteDataSource.sellDog(request, dataCallback);
    }

    @Override
    public void getShoppLog(int pageNo, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getShoppLog(pageNo, dataCallback);
        else mRemoteDataSource.getShoppLog(pageNo, dataCallback);
    }

    @Override
    public void getBankAccount(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getBankAccount( dataCallback);
        else mRemoteDataSource.getBankAccount( dataCallback);
    }

    @Override
    public void getApproveBank(CardEditRequset requset, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getApproveBank(requset, dataCallback);
        else mRemoteDataSource.getApproveBank(requset, dataCallback);
    }

    @Override
    public void getApproveSwift(CardEditRequset requset, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.getApproveSwift(requset, dataCallback);
        else mRemoteDataSource.getApproveSwift(requset, dataCallback);
    }

    @Override
    public void applyMerchant(MerchantRequest requset, DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.applyMerchant(requset, dataCallback);
        else mRemoteDataSource.applyMerchant(requset, dataCallback);
    }

    @Override
    public void cancleMerchant(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.cancleMerchant( dataCallback);
        else mRemoteDataSource.cancleMerchant( dataCallback);
    }

    @Override
    public void merchantStatus(DataCallback dataCallback) {
        if (isLocal) mLocalDataSource.merchantStatus(dataCallback);
        else mRemoteDataSource.merchantStatus(dataCallback);
    }

}
