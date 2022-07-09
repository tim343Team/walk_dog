package com.wallet.walkthedog.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.RootApplication;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;

public class WalkNoticeDialog extends BaseDialogFragment {
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_speed)
    TextView txtSpeed;

    private String type;//1:超速  2：低速
    private String speed;
    private CountDownTimer timer;
    private boolean shown=false;

    @OnClick(R.id.txt_back)
    void back() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        dismiss();
    }

    public static WalkNoticeDialog newInstance(String type, String speed) {
        WalkNoticeDialog fragment = new WalkNoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("speed", speed);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_walk_notice;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        speed = bundle.getString("speed");
        if (type.equals("1")) {
            txtStatus.setText(getResources().getString(R.string.speed_down));
        } else {
            txtStatus.setText(getResources().getString(R.string.speed_up));
        }
        txtSpeed.setText(RootApplication.getInstance().getString(R.string.speed, speed + " km/h"));
        fillCodeView(3 * 60 * 1000);
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (shown) return;
        super.show(manager, tag);
        shown = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        shown = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDismiss(dialog);
    }

    public boolean isShown() {
        return shown;
    }

    private void fillCodeView(long time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText(millisUntilFinished / 1000 + " ");
            }

            @Override
            public void onFinish() {
                //结束回调
                callback.callback();
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback();
    }
}
