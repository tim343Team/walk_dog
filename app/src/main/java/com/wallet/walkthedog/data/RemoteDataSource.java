package com.wallet.walkthedog.data;

import com.google.gson.reflect.TypeToken;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.SendMailboxCodeDao;
import com.wallet.walkthedog.dao.request.SendMailboxCodeRequest;

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
                .addParams("email",request.getEmail())// addParams可以传递post方法的参数
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
                                SendMailboxCodeDao obj = gson.fromJson(object.getJSONObject("data").toString(), SendMailboxCodeDao.class);
                                dataCallback.onDataLoaded(obj);
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
}
