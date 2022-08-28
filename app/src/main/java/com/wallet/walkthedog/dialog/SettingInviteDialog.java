package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.NumberAdapter;
import com.wallet.walkthedog.untils.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.DateTimeUtil;
import tim.com.libnetwork.view.PickTimeView;

public class SettingInviteDialog extends BaseDialogFragment {
    @BindView(R.id.ll_picker)
    View llPicker;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.recyclerview_hour)
    RecyclerView hourRecycler;
    @BindView(R.id.recyclerview_minute)
    RecyclerView minutRecycler;

    private List<Integer> hourList = new ArrayList<>();
    private List<Integer> minutList = new ArrayList<>();
    private int isShowType = 0;
    String startTime = null;
    String endTime = null;

    @OnClick(R.id.ll_start)
    void selectStart() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        PickTimeView timeView = new PickTimeView(getContext());
        timeView.setType(new boolean[]{false, false, false, true, true, false});
        timeView.setStartTime(year, month, day, 0, 0, 0);
        timeView.setEndtTime(year, month, day, 24, 59, 0);
        timeView.setDialog(true);
        timeView.setCancelMessage("");
        timeView.setSubmitMessage(getResources().getString(R.string.confirm));
        timeView.setTitle(getResources().getString(R.string.select_time));
        timeView.showTimePickerView();
        timeView.setOnTimeSelectListener(new PickTimeView.OnTimeSelect() {
            @Override
            public void onSelect(Date date) {
                DateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startTime=df2.format(date);
                tvStartTime.setText(df.format(date));
            }
        });
    }

    @OnClick(R.id.ll_end)
    void selectEnd() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        PickTimeView timeView = new PickTimeView(getContext());
        timeView.setType(new boolean[]{false, false, false, true, true, false});
        timeView.setStartTime(year, month, day, 0, 0, 0);
        timeView.setEndtTime(year, month, day, 24, 59, 0);
        timeView.setDialog(true);
        timeView.setCancelMessage("");
        timeView.setSubmitMessage(getResources().getString(R.string.confirm));
        timeView.setTitle(getResources().getString(R.string.select_time));
        timeView.showTimePickerView();
        timeView.setOnTimeSelectListener(new PickTimeView.OnTimeSelect() {
            @Override
            public void onSelect(Date date) {
                DateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime=df2.format(date);
                tvEndTime.setText(df.format(date));
            }
        });
//        showPicker(2);
    }

    @OnClick(R.id.txt_confirm_time)
    void confirmTime() {
        if (isShowType == 1) {
            tvStartTime.setText("55");
        } else if (isShowType == 2) {
            tvEndTime.setText("55");
        }
    }

    @OnClick(R.id.txt_confirm)
    void confirm() {
        if(startTime==null || startTime.isEmpty()){
            ToastUtils.shortToast(getContext(),R.string.start_time_hint);
            return;
        }
        if(endTime==null || endTime.isEmpty()){
            ToastUtils.shortToast(getContext(),R.string.end_time_hint);
            return;
        }
        callback.callback(startTime, endTime);
    }

    @OnClick({R.id.txt_cancle, R.id.back})
    void cancle() {
        dismiss();
    }

    public static SettingInviteDialog newInstance() {
        SettingInviteDialog fragment = new SettingInviteDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_setting_invite;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        minutRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private void showPicker(int type) {
        llPicker.setVisibility(View.VISIBLE);
        hourList.clear();
        minutList.clear();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        for (int i = hour; i < 25; i++) {
            hourList.add(i);
        }
        for (int i = 0; i < 60; i++) {
            minutList.add(i);
        }
        hourRecycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        hourRecycler.setLayoutManager(manager);
        NumberAdapter numberAdapter = new NumberAdapter(getActivity(), hourList, 1);
        hourRecycler.setAdapter(numberAdapter);
        //
        minutRecycler.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        minutRecycler.setLayoutManager(manager2);
        NumberAdapter minutAdapter = new NumberAdapter(getActivity(), minutList, 2);
        minutRecycler.setAdapter(minutAdapter);
        isShowType = type;
    }

    private OperateCallback callback;

    public void setCallback(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void callback(String startTime, String endTime);
    }
}
