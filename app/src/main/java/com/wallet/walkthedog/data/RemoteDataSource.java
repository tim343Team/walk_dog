package com.wallet.walkthedog.data;

import com.google.gson.reflect.TypeToken;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.DogMailDao;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.FriendInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.PropDetailDao;
import com.wallet.walkthedog.dao.TrainDao;
import com.wallet.walkthedog.dao.request.BuyRequest;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.dao.request.MailRequest;
import com.wallet.walkthedog.dao.request.OpreationPropRequest;
import com.wallet.walkthedog.dao.request.FriendRequest;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;
import com.wallet.walkthedog.dao.request.SwitchWalkRequest;
import com.wallet.walkthedog.dao.request.TrainRequest;
import com.wallet.walkthedog.sp.SharedPrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Request;
import tim.com.libnetwork.network.okhttp.StringCallBack;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.WonderfulLogUtils;

import static com.wallet.walkthedog.app.GlobalConstant.JSON_ERROR;
import static com.wallet.walkthedog.app.GlobalConstant.OKHTTP_ERROR;

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
                                dataCallback.onDataLoaded(object.optString("data"));
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
    public void getUserDog(DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getUserDogUrl() + "?pageNo=1&pageSize=50")
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
        WonderfulOkhttpUtils.get().url(UrlFactory.buyDogUrl() + "?id=" + request.getId()+"?password=" +request.getPassword())
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
                                List<DogInfoDao> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<DogInfoDao>>() {
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
    public void getUserProp(int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getUserPropUrl() + "?pageNo=" + pageNo + "&pageSize=20")
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
        WonderfulOkhttpUtils.get().url(UrlFactory.trainDogUrl()+"?dogId=" + request.getDogId() + "&trainId=" + request.getTrainId())
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
        WonderfulOkhttpUtils.get().url(UrlFactory.upDogLevelUrl()+"?dogId=" + dogId)
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
    public void getFriendList(int pageNo, DataCallback dataCallback) {
        WonderfulOkhttpUtils.get().url(UrlFactory.getFriendListUrl()+"?pageNo=" + pageNo + "&pageSize=20")
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
        WonderfulOkhttpUtils.get().url(UrlFactory.sendFriendInvitedUrl()+"?friendEmail=" + friendEmail )
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
        WonderfulOkhttpUtils.get().url(UrlFactory.friendDogDetailUrl()+"?id=" + id )
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
        WonderfulOkhttpUtils.get().url(UrlFactory.setNoteUrl()+"?friendListId=" + request.getId() +"&name="+request.getName())
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
        WonderfulOkhttpUtils.get().url(UrlFactory.delFriendUrl()+"?friendMemberId=" + request.getFriendMemberId())
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
                                dataCallback.onDataLoaded(object.optInt("message"));
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
        WonderfulOkhttpUtils.get().url(UrlFactory.dogListUrl()+"?pageNo=" + pageNo + "&pageSize=20")
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
        WonderfulOkhttpUtils.get().url(UrlFactory.propListUrl()+"?pageNo=" + pageNo + "&pageSize=20")
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
}
