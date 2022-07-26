package com.wallet.walkthedog.view.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.HomePropsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.DogFoodDao;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dao.TrainDao;
import com.wallet.walkthedog.dao.request.TrainRequest;
import com.wallet.walkthedog.db.dao.PropCache;
import com.wallet.walkthedog.dialog.BuyFoodDialog;
import com.wallet.walkthedog.dialog.DayLimitDialog;
import com.wallet.walkthedog.dialog.FeedingDialog;
import com.wallet.walkthedog.dialog.FeedofRemindDialog;
import com.wallet.walkthedog.dialog.HungryDialog;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.InvitedDialog;
import com.wallet.walkthedog.dialog.InvitedStopDialog;
import com.wallet.walkthedog.dialog.MoreOperationDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.SettingInviteDialog;
import com.wallet.walkthedog.dialog.TrainDogDialog;
import com.wallet.walkthedog.dialog.TrainListDialog;
import com.wallet.walkthedog.dialog.UpgradeDialog;
import com.wallet.walkthedog.dialog.UpgradeDialog_ViewBinding;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.invite.InviteActivity;
import com.wallet.walkthedog.view.props.ChoicePropsActivity;
import com.wallet.walkthedog.view.select_dog.SelectDogActivity;
import com.wallet.walkthedog.view.select_dog.SelectPresenter;
import com.wallet.walkthedog.view.walk.WalkActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseTransFragment;
import tim.com.libnetwork.view.PickTimeView;

public class HomeFragment extends BaseTransFragment implements HomeContract.HomeView {
    public static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.ll_null_dog)
    View viewNullDog;
    @BindView(R.id.ll_have_dog)
    View viewDog;
    @BindView(R.id.ll_dog_center)
    RelativeLayout viewBg;
    @BindView(R.id.txt_dog_name)
    TextView txtDogName;
    @BindView(R.id.img_gender)
    ImageView imgGender;
    @BindView(R.id.txt_level)
    TextView txtDogLevel;
    @BindView(R.id.txt_level_2)
    TextView txtDogLevel2;
    @BindView(R.id.txt_state)
    TextView txtState;//体力状态
    @BindView(R.id.txt_status)
    TextView txtStatus;//？？
    @BindView(R.id.ll_walk_dog)
    View viewWalkDog;//狗狗状态按钮
    @BindView(R.id.img_walk_dog)
    ImageView imgWalkDog;//狗狗状态按钮图标
    @BindView(R.id.txt_walk_dog)
    TextView txtWalkDog;//狗狗状态描述
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindViews({R.id.img_equipment_1, R.id.img_equipment_2, R.id.img_equipment_3})
    ImageView[] imgEquipments;
    @BindView(R.id.progress_bg)
    ImageView progressBg;
    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.progress_txt)
    TextView progressTxt;
    @BindView(R.id.txt_speed)
    TextView txtSpeed;
    @BindView(R.id.txt_number)
    TextView txtNumber;
    @BindView(R.id.txt_trip)
    TextView txtTrip;
    @BindView(R.id.txt_region)
    TextView txtRegion;

    private HomeContract.HomePresenter presenter;
    private int progressAll = 0;
    private String totalFood = "0";
    private String totalProperty = "0";
    private HomePropsAdapter adapter;
    private DogInfoDao mDefultDogInfo;

    @OnClick(R.id.ll_add_dog)
    void addDoag() {
        //TODO
//        SettingInviteDialog dialog = SettingInviteDialog.newInstance();
//        dialog.setTheme(R.style.PaddingScreen);
//        dialog.setGravity(Gravity.BOTTOM);
//        dialog.show(getFragmentManager(), "edit");
//        dialog.setCallback(new SettingInviteDialog.OperateCallback() {
//            @Override
//            public void callback() {
//                PickTimeView timeView = new PickTimeView(getmActivity());
//                timeView.setStartTime(1970, 1, 1, 0, 0, 0);
//                timeView.setEndtTimeMillis();
//                timeView.setTitle("生日");
//                timeView.showTimePickerView();
//                timeView.setOnTimeSelectListener(new PickTimeView.OnTimeSelect() {
//                    @Override
//                    public void onSelect(Date date) {
////                DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
////                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
////                tvBirthday.setText(df.format(date));
////                bean.setBirthday(df2.format(date));
//                    }
//                });
//            }
//        });
        SelectDogActivity.actionStart(getActivity());
    }

    @OnClick(R.id.img_invate)
    void addInvate() {
        InviteActivity.actionStart(getmActivity());
    }

    @OnClick(R.id.img_more)
    void startMore() {
        if (mDefultDogInfo == null) {
            return;
        }
        MoreOperationDialog dialog = MoreOperationDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        dialog.setFeedCallback(new MoreOperationDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //餵食
                //查询喂食消耗
                showFeeding();
                dialog.dismiss();
            }
        });
        dialog.setReplaceCallback(new MoreOperationDialog.OperateReplaceCallback() {
            @Override
            public void callback() {
                //替換狗狗
                SelectDogActivity.actionStart(getActivity());
                dialog.dismiss();
            }
        });
        dialog.setTrainCallback(new MoreOperationDialog.OperateTrainCallback() {
            @Override
            public void callback() {
                //获取训练列表
                presenter.getAllTrain();
            }
        });
    }

    @OnClick(R.id.img_identity)
    void startIdentity() {
        if (mDefultDogInfo == null) {
            return;
        }
        IdentityDialog dialog = IdentityDialog.newInstance(mDefultDogInfo);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @OnClick(R.id.ll_walk_dog)
    void startWalking() {
        if (mDefultDogInfo.getStarvation()==1) {
            //飢餓狀態
            HungryDialog dialog2 = HungryDialog.newInstance();
            dialog2.setTheme(R.style.PaddingScreen);
            dialog2.setGravity(Gravity.CENTER);
            dialog2.show(getFragmentManager(), "edit");
            dialog2.setFeedCallback(new HungryDialog.OperateFeedCallback() {
                @Override
                public void callback() {
                    showFeeding();
                    dialog2.dismiss();
                }
            });
        } else {
            //正常狀態
            if(mDefultDogInfo.getRateOfProgress()==100){
                //升级
                presenter.upDogLevel(mDefultDogInfo.getId());
            }else {
                //判断遛狗次数
                if (mDefultDogInfo.getDayLimit() < 2) {
                    WalkActivity.actionStart(getmActivity(),mDefultDogInfo);
                } else {
                    DayLimitDialog dialog = DayLimitDialog.newInstance(getResources().getString(R.string.walk_number), getResources().getString(R.string.walk_notice_4));
                    dialog.setTheme(R.style.PaddingScreen);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show(getFragmentManager(), "edit");
                }
            }

        }
    }

    @OnClick({R.id.img_equipment_1, R.id.img_equipment_2, R.id.img_equipment_3})
    void addEquipment(View v) {
//        switch (v.getId()) {
//            case R.id.img_equipment_1:
//                break;
//            case R.id.img_equipment_2:
//                break;
//            case R.id.img_equipment_3:
//                break;
//            default:
//                break;
//        }
        if (mDefultDogInfo == null) {
            return;
        }
        ChoicePropsActivity.actionStart(getmActivity(), mDefultDogInfo.getId());
    }


    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new HomePresenter(Injection.provideTasksRepository(getmActivity()), this);//初始化presenter
//        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                setProgress();
//                getActivity().getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
//                return false;
//            }
//        });
        //TODO 测试邀请遛狗,停止遛狗
//        InvitedDialog dialog = InvitedDialog.newInstance();
//        dialog.setTheme(R.style.PaddingScreen);
//        dialog.setGravity(Gravity.CENTER);
//        dialog.show(getFragmentManager(), "edit");
//        dialog.setRefuseCallback(new InvitedDialog.OperateRefuseCallback() {
//            @Override
//            public void callback() {
//
//            }
//        });
//        dialog.setAcceptCallback(new InvitedDialog.OperateAcceptCallback() {
//            @Override
//            public void callback() {
//
//            }
//        });
        InvitedStopDialog dialog = InvitedStopDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        updateData();
    }

    @Override
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdateHomeData event) {
        isNeedLoad = true;
        //刷新主页
        updateData();
    }

    void updateData() {
        presenter.getDogInfo(SharedPrefsHelper.getInstance().getDogId());
    }

    void updateUI() {
//        mDefultDogInfo.setStarvation(1);//TODO 測試飢餓狀態
        if (mDefultDogInfo == null) {
            viewNullDog.setVisibility(View.VISIBLE);
            viewDog.setVisibility(View.GONE);
            return;
        }
        viewNullDog.setVisibility(View.GONE);
        viewDog.setVisibility(View.VISIBLE);
        if (mDefultDogInfo.getSex() == 0) {
            imgGender.setBackgroundResource(R.mipmap.icon_male);
        } else {
            imgGender.setBackgroundResource(R.mipmap.icon_female);
        }
        if (mDefultDogInfo.getStarvation() == 2) {
            txtState.setText(R.string.full_of_energy);
            txtState.setTextColor(getResources().getColor(R.color.white));
            viewBg.setBackgroundResource(R.mipmap.bg_home_normal);
            viewWalkDog.setBackgroundResource(R.drawable.walk_gradual_round);
            txtWalkDog.setText(R.string.walking);
            if(mDefultDogInfo.getRateOfProgress()==100){
                //升级
                imgWalkDog.setBackgroundResource(R.mipmap.icon_update);
            }else {
                imgWalkDog.setBackgroundResource(R.mipmap.icon_walking);
            }
        } else if(mDefultDogInfo.getStarvation() == 1){
            txtState.setText(R.string.full_of_hunger);
            txtState.setTextColor(getResources().getColor(R.color.color_ffbe0d));
            viewBg.setBackgroundResource(R.mipmap.bg_home_hungry);
            viewWalkDog.setBackgroundResource(R.drawable.hungry_gradual_round);
            txtWalkDog.setText(R.string.feeding);
            imgWalkDog.setBackgroundResource(R.mipmap.icon_feeding);//图标变形
        }else{
            txtState.setText("状态字段返回错误为："+mDefultDogInfo.getStarvation());
            viewBg.setBackgroundResource(R.mipmap.bg_home_normal);
        }
        txtDogLevel2.setText("LEVEL " + mDefultDogInfo.getLevel());
        txtDogLevel.setText("LV. " + mDefultDogInfo.getLevel());
        txtSpeed.setText(String.format(getString(R.string.trip_totle), mDefultDogInfo.getWalkTheDogKm() + ""));
        txtNumber.setText(String.format(getString(R.string.number_totle), mDefultDogInfo.getWalkTheDogCount() + ""));
        txtTrip.setText(String.format(getString(R.string.time_totle), mDefultDogInfo.getWalkTheDogTime() + ""));
        txtRegion.setText(String.format(getString(R.string.number_today), mDefultDogInfo.getDayLimit() + "/2"));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_null_dog)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
        Glide.with(getmActivity()).load(mDefultDogInfo.getImg()).apply(options).into(imgDog);
        setProgress(progressBg, progressBar, progressTxt, mDefultDogInfo.getRateOfProgress() / 100.00);
        //道具
        for(ImageView imageView:imgEquipments){
            RequestOptions optionsEq = new RequestOptions()
                    .centerCrop();
            Glide.with(getmActivity()).load(R.mipmap.icon_add_equipment).apply(optionsEq).into(imageView);
        }
        for (int i = 0; i < mDefultDogInfo.getPropList().size(); i++) {
            if (mDefultDogInfo.getPropList().get(i).getId() == null) {
                continue;
            }
            RequestOptions optionsEq = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_bell)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
            Glide.with(getmActivity()).load(mDefultDogInfo.getPropList().get(i).getImg()).apply(optionsEq).into(imgEquipments[i]);
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    //设置进度条
    private void setProgress(View progressBg, View progressBar, TextView progressTxt, double percentage) {
        progressBg.post(new Runnable() {
            @Override
            public void run() {
                int progressAll = progressBg.getWidth();
                int progress = (int) (progressAll * percentage);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
                params.width = progress;
                progressBar.setLayoutParams(params);
                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
                if (percentage < 0.2) {
                    params2.leftMargin = 0;
                } else if (percentage > 0.9) {
                    params2.leftMargin = (int) (progress - progressAll * 0.3);
                } else {
                    params2.leftMargin = (int) (progress - progressAll * 0.18);
                }
                progressTxt.setLayoutParams(params2);
                progressTxt.setText(percentage * 100 + "%");
            }
        });
    }

    private void showFeeding() {
        presenter.getFeedDog(mDefultDogInfo.getId());
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(toastMessage);
    }

    @Override
    public void getCurrentDogInfo(DogInfoDao dogInfoDao) {
        mDefultDogInfo = dogInfoDao;
        presenter.getWallet("1");//获取代币总数
        presenter.getWallet("2");//获取狗粮总数
        updateUI();
    }

    @Override
    public void getFeedDogInfo(String data) {
        //喂食弹唱
        FeedingDialog dialog = FeedingDialog.newInstance(mDefultDogInfo, data, totalFood);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        dialog.setFeedCallback(new FeedingDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //調用餵食接口
                presenter.feedDog(mDefultDogInfo.getId());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void getWalletInfo(String data, String type) {
        if (type.equals("1")) {
            totalProperty = data;
        } else if (type.equals("2")) {
            totalFood = data;
        }
    }

    @Override
    public void trainListSuccessful(List<TrainDao> data) {
        //訓練狗狗
        TrainListDialog trainDialog = TrainListDialog.newInstance();
        trainDialog.setTheme(R.style.PaddingScreen);
        trainDialog.setGravity(Gravity.CENTER);
        trainDialog.show(getFragmentManager(), "edit");
        trainDialog.setData(data);
        trainDialog.setCallback(new TrainListDialog.OperateCallback() {
            @Override
            public void callback(TrainDao item) {
                TrainDogDialog trainDogDialog = TrainDogDialog.newInstance(item,totalFood);
                trainDogDialog.setTheme(R.style.PaddingScreen);
                trainDogDialog.setGravity(Gravity.CENTER);
                trainDogDialog.show(getFragmentManager(), "edit");
                trainDogDialog.setCallback(new TrainDogDialog.OperateCallback() {
                    @Override
                    public void callback(int trainId) {
                        //获取训练详情
                        presenter.trainDog(new TrainRequest(mDefultDogInfo.getId(),trainId+""));
                        trainDogDialog.dismiss();
                    }
                });
                trainDialog.dismiss();
            }
        });
    }

    @Override
    public void feedSuccessful(String data) {
        //喂食成功,更新数据
        ToastUtils.shortToast(data);
        updateData();
    }

    @Override
    public void feedFail(Integer code, String toastMessage) {
        //喂食失败
        if (code == 1) {
            //TODO 缺少接口
            presenter.getShopDogFood();
        }
    }

    @Override
    public void trainSuccessful(String data) {
        NormalDialog dialog = NormalDialog.newInstance(R.string.successful, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @Override
    public void updateSuccessful(String data) {
        UpgradeDialog dialog = UpgradeDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        updateData();
    }

    @Override
    public void getShopDogFoodSuccessful(DogFoodDao data) {
        //新增是否购买页面
        FeedofRemindDialog dialog = FeedofRemindDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show(getFragmentManager(), "edit");
        dialog.setCallback(new FeedofRemindDialog.OperateCallback() {

            @Override
            public void callback() {
                BuyFoodDialog buyDialog = BuyFoodDialog.newInstance(totalProperty,data);
                buyDialog.setTheme(R.style.PaddingScreen);
                buyDialog.setGravity(Gravity.CENTER);
                buyDialog.show(getFragmentManager(), "edit");
                buyDialog.setCallback(new BuyFoodDialog.OperateCallback() {
                    @Override
                    public void callback() {
                        PasswordDialog passwordDialog = PasswordDialog.newInstance();
                        passwordDialog.setTheme(R.style.PaddingScreen);
                        passwordDialog.setGravity(Gravity.CENTER);
                        passwordDialog.show(getFragmentManager(), "edit");
                        passwordDialog.setCallback(new PasswordDialog.OperateCallback() {
                            @Override
                            public void callback(String password) {
                                //TODO 購買狗糧
                                ToastUtils.shortToast("暫無接口");
                                passwordDialog.dismiss();
                                buyDialog.dismiss();
                            }
                        });
                        passwordDialog.setCallback(new PasswordDialog.OperateErrorCallback() {
                            @Override
                            public void callback() {
                                ToastUtils.shortToast("错误");
                            }
                        });
                    }
                });
                dialog.dismiss();
            }
        });
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        this.presenter = presenter;
    }
}
