package com.wallet.walkthedog.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.dao.DogInfoDao;
import com.wallet.walkthedog.dao.InviteNoticeDao;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseDialogFragment;
import tim.com.libnetwork.utils.DateTimeUtil;

public class InviteNoticeDialog extends BaseDialogFragment {
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_notice)
    TextView txtNotice;
    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndtTime;

    private InviteNoticeDao inviteNoticeDao;

    @OnClick(R.id.txt_refuse)
    void refuse() {
        refuseCallback.callback();
    }

    @OnClick(R.id.txt_accept)
    void accept() {
        acceptCallback.callback();
    }

    @OnClick(R.id.back)
    void back() {
        dismiss();
    }

    public static InviteNoticeDialog newInstance(InviteNoticeDao inviteNoticeDao) {
        InviteNoticeDialog fragment = new InviteNoticeDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("inviteNoticeDao", inviteNoticeDao);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_invited;
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
        inviteNoticeDao = (InviteNoticeDao) bundle.getSerializable("inviteNoticeDao");
        if (inviteNoticeDao == null) {
            return;
        }
        long timecurrentTimeMillis = System.currentTimeMillis()+1000*60*10;
        txtTime.setText(DateTimeUtil.getDetailPayTime(timecurrentTimeMillis));
        txtNotice.setText((String.format(getString(R.string.invitation_together_2), inviteNoticeDao.getFriendName(),inviteNoticeDao.getFriendName())));
        txtStartTime.setText((String.format(getString(R.string.start_time_s), inviteNoticeDao.getEndTime())));
        txtEndtTime.setText((String.format(getString(R.string.end_time_s), inviteNoticeDao.getEndTime())));
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    private OperateRefuseCallback refuseCallback;

    public void setRefuseCallback(OperateRefuseCallback refuseCallback) {
        this.refuseCallback = refuseCallback;
    }

    public interface OperateRefuseCallback {
        void callback();
    }

    private OperateAcceptCallback acceptCallback;

    public void setAcceptCallback(OperateAcceptCallback acceptCallback) {
        this.acceptCallback = acceptCallback;
    }

    public interface OperateAcceptCallback {
        void callback();
    }
}
