package com.wallet.walkthedog.data;

import com.google.gson.reflect.TypeToken;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.AwardDao;
import com.wallet.walkthedog.dao.BoxDao;
import com.wallet.walkthedog.dao.BusinessAuthDao;
import com.wallet.walkthedog.dao.CardInfoDao;
import com.wallet.walkthedog.dao.CodeDataDao;
import com.wallet.walkthedog.dao.CoordDao;
import com.wallet.walkthedog.dao.DogBoxDao;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.FeedDogFoodDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.InviteNoticeDao;
import com.wallet.walkthedog.dao.InvitedFriendDao;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.PropMailDao;
import com.wallet.walkthedog.dao.SellRecordDao;
import com.wallet.walkthedog.dao.StartWalkDao;
import com.wallet.walkthedog.dao.TrainDao;
import com.wallet.walkthedog.dao.VersionInfoDao;
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
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.Request;
import tim.com.libnetwork.network.okhttp.StringCallBack;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.WonderfulLogUtils;

import static com.wallet.walkthedog.app.GlobalConstant.JSON_ERROR;
import static com.wallet.walkthedog.app.GlobalConstant.OKHTTP_ERROR;

import android.util.Log;

public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    private RemoteDataSource() {

    }

    @Override
    public void checkInvitedCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getCheckInvitedUrl())
                .addParams("email", request.getEmail())// addParams可以传递post方法的参数
                .addParams("checkCode",request.getCheckCode())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("验证邀请码:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("验证邀请码：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("data"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void sendMailboxCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        //get请求
//        WonderfulOkhttpUtils.get().url(UrlFactory.getSendMailboxUrl() + "?email=" + request.getEmail())
        //post方法
        WonderfulOkhttpUtils.post().url(UrlFactory.getSendMailboxUrl())
                .addParams("email", request.getEmail())// addParams可以传递post方法的参数
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("发送邮箱验证码:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("发送邮箱验证码：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                //举例：解析data为jsonObject的数据
                                dataCallback.onDataLoaded(object.optString("message"));
                                //举例：解析data为jsonArray的数据
//                                List<SendMailboxCodeDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<SendMailboxCodeDao>>() {
//                                }.getType());
//                                dataCallback.onDataLoaded(objs);
                                //举例：解析data下txList为jsonArray的数据
//                                List<SendMailboxCodeDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("txList").toString(), new TypeToken<List<SendMailboxCodeDao>>() {
//                                }.getType());
//                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void emailCheckCode(SendMailboxCodeRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getEmailLoginUrl())
                .addParams("email", request.getEmail())
                .addParams("checkCode", request.getCheckCode())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("邮箱登录:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("邮箱登录：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                EmailLoginDao obj = gson.fromJson(object.getJSONObject("data").toString(), EmailLoginDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void emailRegister(EmailRegisterRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getEmailRegisterUrl())
                .addParams("email", request.getEmail())
                .addParams("checkCode", request.getCheckCode())
                .addParams("spassword", request.getSpassword())
                .addParams("parentInviteCode", request.getParentInviteCode() == null ? "" : request.getParentInviteCode())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("邮箱注册:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("邮箱注册：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                EmailLoginDao obj = gson.fromJson(object.getJSONObject("data").toString(), EmailLoginDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void emailLogin(EmailLoginRequest request, DataCallback dataCallback) {

    }

    @Override
    public void passwordLogin(EmailLoginRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getPasswordLoginUrl())
                .addParams("username", request.getEmail())
                .addParams("password", request.getPassword())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("密碼登录:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("密碼登录：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                EmailLoginDao obj = gson.fromJson(object.getJSONObject("data").toString(), EmailLoginDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getUserDog(int type,int pageNo,DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getUserDogUrl() + "?pageNo="+pageNo+"&pageSize=99"+ "&type="+type)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取我的狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取我的狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<DogInfoDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<DogInfoDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void useDog(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.useDogUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("使用狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("使用狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void removeDog(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.removeDogUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("移除使用中的狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("移除使用中的狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void useDogInfo(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.useDogInfoUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取当前装备狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取当前装备狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                DogInfoDao obj = gson.fromJson(object.getJSONObject("data").toString(), DogInfoDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getDogInfo(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getDogInfoUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("根据id获取狗狗详情:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("根据id获取狗狗详情：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                DogInfoDao obj = gson.fromJson(object.getJSONObject("data").toString(), DogInfoDao.class);
//                                List<PropDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("propList").toString(), new TypeToken<List<PropDao>>() {
//                                }.getType());
//                                obj.setPropDatas(objs);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getUseDog(String dogId, DataCallback dataCallback) {

    }

    @Override
    public void getFeedDog(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getFeedDogUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("查询喂食消耗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("查询喂食消耗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                FeedDogFoodDao  obj = gson.fromJson(object.getJSONObject("data").toString(), FeedDogFoodDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void feedDog(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.feedDogUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("喂食:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("喂食：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getWallet(String type, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWalletUrl() + "?type=" + type)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取钱包余额:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取钱包余额：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("data"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void buyDog(BuyRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.buyDogUrl() + "?id=" + request.getId() + "&password=" + request.getPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("购买狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("购买狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void buyProp(BuyRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.buyPropUrl() + "?id=" + request.getId() + "&password=" + request.getPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("购买道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("购买道具 ：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void cancelSellDog(BuyRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.cancelSellDogUrl() + "?id=" + request.getId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("取消售卖狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("取消售卖狗狗 ：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void cancelSellProp(BuyRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.cancelSellPropUrl() + "?id=" + request.getId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("取消售卖道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("取消售卖道具:", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getSysDataCode(String code, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getSysDataCodeUrl() + "?code=" + code)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("根据code查询:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("根据code查询:", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                CodeDataDao obj = gson.fromJson(object.getJSONObject("data").toString(), CodeDataDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void openBox(OpreationPropRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.openBoxUrl() + "?propId=" + request.getPropId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("开启宝箱:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("开启宝箱:", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                BoxDao obj = gson.fromJson(object.getJSONObject("data").toString(), BoxDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getWalkTheDogFriend(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getWalkTheDogFriendUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取一起遛狗的好友:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取一起遛狗的好友：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                InvitedFriendDao obj = gson.fromJson(object.getJSONObject("data").toString(), InvitedFriendDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void startWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getStartWalkUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("dogId", request.getDogId())
                .addParams("lan", request.getLan())
                .addParams("lon", request.getLon())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("开始遛狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("开始遛狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                StartWalkDao obj = gson.fromJson(object.getJSONObject("data").toString(), StartWalkDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void stopWalkDog(SwitchWalkRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.getStopWalkUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("dogId", request.getDogId())
                .addParams("lan", request.getLan())
                .addParams("lon", request.getLon())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("停止遛狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("停止遛狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getAwardPage(AwardRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getAwardPageUrl() + "?pageNo=1&pageSize=100&logId=" + request.getLogId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取遛狗奖励记录:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取遛狗奖励记录：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<AwardDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<AwardDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getAward(AwardRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getAwardUrl() + "?awardId=" + request.getAwardId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("领取遛狗奖励记录:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("领取遛狗奖励记录：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void addCoord(SwitchWalkRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.addCoordUrl() + "?lan=" + request.getLan() + "&lon=" + request.getLon())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("遛狗坐标传入:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("遛狗坐标传入：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                CoordDao obj = gson.fromJson(object.getJSONObject("data").toString(), CoordDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getUserProp(int type, int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getUserPropUrl() + "?pageNo=" + pageNo + "&pageSize=20" + "&type=" + type)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取我的道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取我的道具：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<PropDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<PropDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getDogProp(String dogId, int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getDogPropUrl() + "?dogId=" + dogId + "&pageNo=" + pageNo + "&pageSize=20")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取狗狗的道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取狗狗的道具：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<PropDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<PropDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getRemoveProp(OpreationPropRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getremovePropUrl() + "?propId=" + request.getPropId() + "&dogId=" + request.getDogId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("移除使用中的道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("移除使用中的道具：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getAddProp(OpreationPropRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getusePropUrl() + "?propId=" + request.getPropId() + "&dogId=" + request.getDogId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("装备道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("装备道具：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getPropDetailInfo(OpreationPropRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getPropInfoUrl() + "?propId=" + request.getPropId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("根据id获取道具详情:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("根据id获取道具详情：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                PropDetailDao obj = gson.fromJson(object.getJSONObject("data").toString(), PropDetailDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void useDogFood(OpreationPropRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.useDogFoodUrl() + "?propId=" + request.getPropId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("使用狗粮:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("使用狗粮：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("data"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void sellProp(SellRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.sellPropUrl() + "?id=" + request.getId() + "&price=" + request.getPrice()
                +"&password="+request.getPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("售卖道具:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("售卖道具：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getAllTrain(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getAllTrainUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取所有训练项目:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取所有训练项目：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<TrainDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<TrainDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void trainDog(TrainRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.trainDogUrl() + "?dogId=" + request.getDogId() + "&trainId=" + request.getTrainId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("训练狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("训练狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void upDogLevel(String dogId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.upDogLevelUrl() + "?dogId=" + dogId)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("升级:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("升级：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getShopDogFood(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getShopDogFoodUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取商城售卖狗粮详情:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取商城售卖狗粮详情：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                DogFoodDao obj = gson.fromJson(object.getJSONObject("data").toString(), DogFoodDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void ShopDogFood(int dogFoodId, int number,String passWord, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.shopDogFoodUrl() + "?dogFoodId=" + dogFoodId + "&number="+number+"&passWord="+passWord)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("购买商城出售的狗粮:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("购买商城出售的狗粮：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getFriendList(int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getFriendListUrl() + "?pageNo=" + pageNo + "&pageSize=20")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("好友列表:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("好友列表：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<FriendInfoDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<FriendInfoDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void friendEmail(String friendEmail, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.sendFriendInvitedUrl() + "?friendEmail=" + friendEmail)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("发送好友邀请:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("发送好友邀请：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getFriendDogDetail(String id, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.friendDogDetailUrl() + "?friendListId=" + id)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("好友狗狗详情:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("好友狗狗详情：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                DogInfoDao obj = gson.fromJson(object.getJSONObject("data").toString(), DogInfoDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void setNote(FriendRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.setNoteUrl() + "?friendListId=" + request.getId() + "&name=" + request.getName())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("设置好友备注:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("设置好友备注：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void delFriend(FriendRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.delFriendUrl() + "?friendMemberId=" + request.getFriendMemberId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("移除好友:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("移除好友：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void addTogether(InviteRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.addTogetherUrl() + "?friendId=" + request.getFriendId()+ "&startTime=" +request.getStartTime()
                        + "&endTime=" +request.getEndTime())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("发起遛狗邀请:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("发起遛狗邀请：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void deleteTogether(String togetherId, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.deleteTogetherUrl() + "?togetherId=" + togetherId+ "&startTime=")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("移除一起遛狗关系:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("移除一起遛狗关系：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getNewTogethersUrl(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getNewTogethersUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取最新遛狗邀请通知:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取最新遛狗邀请通知：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<InviteNoticeDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<InviteNoticeDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getVersionInfo(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getLastRevisionUrl() + "?platform=0")
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("获取版本信息:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("获取版本信息：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                VersionInfoDao obj = gson.fromJson(object.getJSONObject("data").toString(), VersionInfoDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void ideaTogether(String togetherId, int status, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.ideaTogetherUrl() + "?togetherId=" + togetherId + "&status="+status)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("一起遛狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("一起遛狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getDogList(MailRequest request, int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.dogListUrl() + "?pageNo=" + pageNo + "&pageSize=20"
                        + "&priceSort=" + request.getPriceSort()
                        + "&nftCatagoryId=" + request.getNftCatagoryId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("狗狗商城列表:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("狗狗商城列表：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<DogMailDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<DogMailDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getPropList(MailRequest request, int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.propListUrl() + "?pageNo=" + pageNo + "&pageSize=20"
                        + "&priceSort=" + request.getPriceSort()
                        + "&nftCatagoryId=" + request.getNftCatagoryId())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("道具商城列表:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("道具商城列表：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<PropMailDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<PropMailDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getPropDownBox(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.propDownBoxUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("道具种类下拉框:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("道具种类下拉框：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<DogBoxDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<DogBoxDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getDogDownBox(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.dogDownBoxUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("狗狗种类下拉框:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("狗狗种类下拉框：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<DogBoxDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<DogBoxDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void sellDog(SellRequest request, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.sellDogUrl() + "?id=" + request.getId() + "&price=" + request.getPrice()
                +"&password="+request.getPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("售卖狗狗:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("售卖狗狗：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getShoppLog(int type,int pageNo, DataCallback dataCallback) {
        //1是道具 2狗狗
        WonderfulOkhttpUtils.get().url(UrlFactory.getShoppLogUrl() + "?pageNo="+pageNo+"&pageSize=20"+"&nftBaseType="+type)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("购买出售记录:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("购买出售记录：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<SellRecordDao> objs = gson.fromJson(object.getJSONObject("data").getJSONArray("records").toString(), new TypeToken<List<SellRecordDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getBankAccount(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getBankAccountUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("账户设置详情:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("账户设置详情：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<CardInfoDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<CardInfoDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getApproveBank(CardEditRequset requset, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.bindBankAccountUrl()+"?realName="+requset.getRealName()+"&bank="+requset.getBank()
                        +"&cardNo="+requset.getCardNo()+"&jyPassword="+requset.getJyPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("设置银行卡:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("设置银行卡：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getApproveSwift(CardEditRequset requset, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.bindSwiftAccountUrl()+"?swiftAddress="+requset.getSwiftAddress()+"&swiftRealName="+requset.getSwiftRealName()
                        +"&password="+requset.getPassword())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("绑定swift:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("绑定swift：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void applyMerchant(MerchantRequest requset, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.applyMerchantUrl()+"?email="+requset.getEmail()+"&assetImg="+requset.getAssetImg())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("认证商家申请:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("认证商家申请：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void getApproveBusiness(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.businessAuthUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("保证金类型:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("保证金类型：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                List<BusinessAuthDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<BusinessAuthDao>>() {
                                }.getType());
                                dataCallback.onDataLoaded(objs);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void cancleMerchant(DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.cancleMerchantUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addParams("detail","null")
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("取消商家申请:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("取消商家申请：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    @Override
    public void merchantStatus(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.statusMerchantUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("认证商家申请状态:", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("认证商家申请状态：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                MerchantStatusDao obj = gson.fromJson(object.getJSONObject("data").toString(), MerchantStatusDao.class);
                                dataCallback.onDataLoaded(obj);
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    public void uploadFile(String fileName, File file, final DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.uploadImgUrl())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .addFile("file", fileName, file)
                .build().execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("上传文件接口", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("上传文件接口：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("data"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }

    public void updateChatHead(String url,  final DataCallback dataCallback) {
        WonderfulOkhttpUtils.post().url(UrlFactory.updateAvatar()+"?url="+url)
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build().execute(new StringCallBack() {
                    @Override
                    public void onError(Request request, Exception e) {
                        super.onError(request, e);
                        WonderfulLogUtils.logi("更新头像", e.getMessage());
                        dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                    }

                    @Override
                    public void onResponse(String response) {
                        WonderfulLogUtils.logi("更新头像：", response.toString());
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.optInt("code") == 0) {
                                dataCallback.onDataLoaded(object.optString("message"));
                            } else {
                                dataCallback.onDataNotAvailable(object.getInt("code"), object.optString("error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dataCallback.onDataNotAvailable(JSON_ERROR, null);
                        }
                    }
                });
    }
}
