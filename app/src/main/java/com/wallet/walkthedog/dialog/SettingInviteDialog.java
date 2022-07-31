package com.wallet.walkthedog.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.adapter.CreateeMnemoricAdapter;
import com.wallet.walkthedog.adapter.NumberAdapter;
import com.wallet.walkthedog.view.login.CreateActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
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

    @OnClick(R.id.ll_start)
    void selectStart() {
         PickTimeView timeView = new PickTimeView(getDialog().getContext());
          timeView.setStartTime(1970, 1, 1, 0, 0, 0);
          timeView.setEndtTimeMillis();
          timeView.setTitle("生日");
          timeView.showTimePickerView();
          timeView.setOnTimeSelectListener(new PickTimeView.OnTimeSelect() {
              @Override
              public void onSelect(Date date) {
//                  DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
//                  DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//                  tvBirthday.setText(df.format(date));
//                  bean.setBirthday(df2.format(date));
              }
          });
//        showPicker(1);
    }

    @OnClick(R.id.ll_end)
    void selectEnd() {
        showPicker(2);
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
        String startTime = null;
        String endTime = null;
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
