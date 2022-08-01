package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.dao.WalletsItem;
import com.wallet.walkthedog.dialog.QrDialog;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.Utils;
import com.wallet.walkthedog.view.dog.MyDogActivity;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.home.HomeFragment;
import com.wallet.walkthedog.view.login.LoginActivity;
import com.wallet.walkthedog.view.mine.otc.OTCOrderActivity;
import com.wallet.walkthedog.view.props.MyPropsActivity;

import java.util.List;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseTransFragment;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;

public class MineFragment extends BaseTransFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.avatar)
    ImageView ivHeader;
    boolean see = true;

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void init() {
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AvatarActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.iv_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO url地址
                QrDialog qrDialog = QrDialog.newInstance("xxxxx");
                qrDialog.show(getChildFragmentManager(), "");

            }
        });
        rootView.findViewById(R.id.ll_dog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //我的狗狗
                MyDogActivity.actionStart(getmActivity());
            }
        });
        rootView.findViewById(R.id.ll_prop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //我的装备
                MyPropsActivity.actionStart(getmActivity());
            }
        });
        rootView.findViewById(R.id.layout_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MyAssetActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_walk_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), DogWalkRecordActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_invited_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), InvitedDogActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_train_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //训练记录
                Intent intent = new Intent(requireContext(), TrainRecordActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LanguageSetActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_security).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SecuritySetActivity.class);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.ll_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_invite_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), FriendListActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_mine_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MineFoodActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.ll_otc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), OTCOrderActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefsHelper.getInstance().saveUserInfo(null);
                SharedPrefsHelper.getInstance().saveToken("");
                Intent intent = new Intent(requireActivity(), EmailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ImageView iv_see = rootView.findViewById(R.id.iv_see);
        iv_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see = !see;
                if (userInfo != null) {
                    updateUserInfo(userInfo);
                }
                if (see){
                    iv_see.setImageResource(R.mipmap.icon_eye_close);
                }else {
                    iv_see.setImageResource(R.mipmap.icon_eye_see);
                }
            }
        });
        iv_see.setImageResource(R.mipmap.icon_eye_close);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && rootView != null) {
            getUserInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao dao) {
                updateUserInfo(dao);
            }
        });
    }

    private void getUserInfo() {
        WonderfulOkhttpUtils.get().url(UrlFactory.userinfo())
                .addHeader("access-auth-token", SharedPrefsHelper.getInstance().getToken())
                .build()
                .getCall()
                .enqueue(new GsonWalkDogCallBack<RemoteData<UserInfoDao>>() {
                    @Override
                    protected void onRes(RemoteData<UserInfoDao> testRemoteData) {
                        onSuccessUserInfo(testRemoteData.getNotNullData());
                    }
                });
    }

    UserInfoDao userInfo;

    private void onSuccessUserInfo(UserInfoDao userinfo) {
        SharedPrefsHelper.getInstance().saveUserInfo(userinfo);
        updateUserInfo(userinfo);
    }

    private void updateUserInfo(UserInfoDao userinfo) {
        this.userInfo = userinfo;
        ImageView avatar = rootView.findViewById(R.id.avatar);
        TextView txt_user_name = rootView.findViewById(R.id.txt_user_name);
        TextView tv_asset = rootView.findViewById(R.id.tv_asset);
        TextView tv_dog_food = rootView.findViewById(R.id.tv_dog_food);

        Glide.with(avatar).load(userinfo.getChatHead())
                .apply(RequestOptions.circleCropTransform())
                .into(avatar);
        txt_user_name.setText(userinfo.getName());
        List<WalletsItem> wallets = userinfo.getWallets();
        for (int i = 0; i < wallets.size(); i++) {
            WalletsItem item = wallets.get(i);
            if (item.getType() == 1) {
                String asset = Utils.getFormat("%.2f", item.getDogFood());
                if (see) {
                    tv_asset.setText(asset);
                } else {
                    tv_asset.setText("****");
                }

            } else if (item.getType() == 2) {
                String dogFood = Utils.getFormat("%.2f", item.getDogFood());
                tv_dog_food.setText(dogFood);
            }
        }
    }

    @Override
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }
}
