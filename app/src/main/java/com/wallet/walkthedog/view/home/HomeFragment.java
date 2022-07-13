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

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.HomePropsAdapter;
import com.wallet.walkthedog.adapter.MyPropsAdapter;
import com.wallet.walkthedog.dao.PropDao;
import com.wallet.walkthedog.dialog.BuyFoodDialog;
import com.wallet.walkthedog.dialog.FeedingDialog;
import com.wallet.walkthedog.dialog.HungryDialog;
import com.wallet.walkthedog.dialog.IdentityDialog;
import com.wallet.walkthedog.dialog.InvitedDialog;
import com.wallet.walkthedog.dialog.InvitedStopDialog;
import com.wallet.walkthedog.dialog.MoreOperationDialog;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NoticeDialog;
import com.wallet.walkthedog.dialog.PasswordDialog;
import com.wallet.walkthedog.dialog.TrainDogDialog;
import com.wallet.walkthedog.dialog.TrainListDialog;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.invite.InviteActivity;
import com.wallet.walkthedog.view.props.ChoicePropsActivity;
import com.wallet.walkthedog.view.select_dog.SelectDogActivity;
import com.wallet.walkthedog.view.walk.WalkActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseTransFragment;

public class HomeFragment extends BaseTransFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.ll_null_dog)
    View viewNullDog;
    @BindView(R.id.ll_have_dog)
    View viewDog;
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
    @BindView(R.id.img_dog)
    ImageView imgDog;
    @BindView(R.id.img_equipment_1)
    ImageView imgEquipment1;
    @BindView(R.id.img_equipment_2)
    ImageView imgEquipment2;
    @BindView(R.id.img_equipment_3)
    ImageView imgEquipment3;
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

    private int progressAll = 0;
    private HomePropsAdapter adapter;
    private List<PropDao> data = new ArrayList<>();

    @OnClick(R.id.ll_add_dog)
    void addDoag() {
        //TODO 切换
        viewNullDog.setVisibility(View.GONE);
        viewDog.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.img_invate)
    void addInvate() {
        InviteActivity.actionStart(getmActivity());
    }

    @OnClick(R.id.img_more)
    void startMore() {
        MoreOperationDialog dialog = MoreOperationDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        dialog.setFeedCallback(new MoreOperationDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //餵食
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
                //訓練狗狗
                TrainListDialog trainDialog = TrainListDialog.newInstance();
                trainDialog.setTheme(R.style.PaddingScreen);
                trainDialog.setGravity(Gravity.CENTER);
                trainDialog.show(getFragmentManager(), "edit");
                trainDialog.setCallback(new TrainListDialog.OperateCallback() {
                    @Override
                    public void callback(int status) {
                        TrainDogDialog trainDogDialog = TrainDogDialog.newInstance(status);
                        trainDogDialog.setTheme(R.style.PaddingScreen);
                        trainDogDialog.setGravity(Gravity.CENTER);
                        trainDogDialog.show(getFragmentManager(), "edit");
                        trainDogDialog.setCallback(new TrainDogDialog.OperateCallback() {
                            @Override
                            public void callback() {
                                trainDogDialog.dismiss();
                            }
                        });
                        trainDialog.dismiss();
                    }
                });
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.img_identity)
    void startIdentity() {
        IdentityDialog dialog = IdentityDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
    }

    @OnClick(R.id.ll_walk_dog)
    void startWalking() {
        if (false) {
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
            WalkActivity.actionStart(getmActivity());
        }

        //TODO 测试进度条
        progressAll = progressBg.getWidth();
        setProgress(0.88);
//        String outMessage = String.format(String.valueOf(R.string.text_push_notification_message), "4.23 km/h");

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
        ChoicePropsActivity.actionStart(getmActivity());
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

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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

    }

    @Override
    protected void RefreshloadData() {

    }

    @Override
    protected void initDestroy() {

    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    //设置进度条
    private void setProgress(double percentage) {
        int progress = (int) (progressAll * percentage);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        params.width = progress;
        progressBar.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) progressTxt.getLayoutParams();
        if (percentage < 0.2) {
            params2.leftMargin = 0;
        } else {
            params2.leftMargin = (int) (progress - progressAll * 0.18);
        }
        progressTxt.setLayoutParams(params2);
        progressTxt.setText(percentage * 100 + "%");
    }

    private void showFeeding() {
        FeedingDialog dialog = FeedingDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getFragmentManager(), "edit");
        dialog.setFeedCallback(new FeedingDialog.OperateFeedCallback() {
            @Override
            public void callback() {
                //TODO 調用餵食接口
                BuyFoodDialog buyDialog = BuyFoodDialog.newInstance();
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
                            public void callback() {
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
}
