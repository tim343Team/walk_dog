package com.wallet.walkthedog.view.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.custom_view.PasswordView;
import com.wallet.walkthedog.dao.EmailLoginDao;
import com.wallet.walkthedog.dao.request.EmailLoginRequest;
import com.wallet.walkthedog.dao.request.EmailRegisterRequest;
import com.wallet.walkthedog.data.Constant;
import com.wallet.walkthedog.db.UserDao;
import com.wallet.walkthedog.db.dao.UserCache;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.email.EmailActivity;
import com.wallet.walkthedog.view.email.EmailPresenter;
import com.wallet.walkthedog.view.email.InvitationActivity;
import com.wallet.walkthedog.view.email.LoginTypeActivity;
import com.wallet.walkthedog.view.email.SettingMobileCodeActivity;
import com.wallet.walkthedog.view.home.HomeActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.CharUtil;

public class SettingPassWordActivity extends BaseActivity implements SettingPasswordContract.SettingPasswordView {
    @BindView(R.id.password_edit)
    PasswordView passwordView;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_match)
    TextView txtMatch;

    private SettingPasswordContract.SettingPasswordPresenter presenter;
    private String type;//1:导入助记词 2：创建助记词 3:邮箱登录 4:郵箱註冊
    private String email;
    private String code;
    private String invitedCode;
    private String enterPassword;
    private String password;
    private int status = 0;//0:输入密码 1：确认密码

    @OnClick(R.id.txt_cancle)
    void clearText() {
        finish();
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity, String type) {
        Intent intent = new Intent(activity, SettingPassWordActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, String type, String email) {
        Intent intent = new Intent(activity, SettingPassWordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, String type, String email, String code) {
        Intent intent = new Intent(activity, SettingPassWordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("email", email);
        intent.putExtra("code", code);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, String type, String email, String code, String invitedCode) {
        Intent intent = new Intent(activity, SettingPassWordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("email", email);
        intent.putExtra("code", code);
        intent.putExtra("invitedCode", invitedCode);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_setting_pass;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new SettingPasswordPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
        passwordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                txtMatch.setVisibility(View.INVISIBLE);
                if (content.isEmpty()) {
                    return;
                }
                if (content.length() == 6) {
                    //检查密码只能为数字或者字母
                    if (!checkoutPassword(content)) {
                        NormalErrorDialog dialog = NormalErrorDialog.newInstance(R.string.match_password_error, R.mipmap.icon_normal_no, R.color.color_E12828);
                        dialog.setTheme(R.style.PaddingScreen);
                        dialog.setGravity(Gravity.CENTER);
                        dialog.show(getSupportFragmentManager(), "edit");
                        passwordView.setText("");
                        return;
                    }
                    if (status == 0) {
                        password = content;
                        if(type.equals(Constant.LOGIN_MAIL_LOGIN)){
                            //调用登录接口
                            EmailLoginRequest request=new EmailLoginRequest(email,password);
                            presenter.passwordLogin(request,password);
                        }else if(type.equals(Constant.LOGIN_MAIL_REGISTER)){
                            status = 1;
                            passwordView.setText("");
                            txtTitle.setText(getResources().getString(R.string.enter_your_password));
                        }
                    } else if (status == 1) {
                        if (content.equals(password)) {
                            //两次密码相同
                            txtMatch.setVisibility(View.INVISIBLE);
                            if (type.equals(Constant.LOGIN_MAIL_LOGIN)) {


                            } else if (type.equals(Constant.LOGIN_MAIL_REGISTER)) {
                                //调用注册接口
                                EmailRegisterRequest request = new EmailRegisterRequest();
                                request.setEmail(email);
                                request.setCheckCode(code);
                                request.setSpassword(password);
                                request.setParentInviteCode(invitedCode);
                                presenter.emailRegister(request, password);
                            }
                        } else {
                            //两次密码不相符
                            txtMatch.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void obtainData() {
        type = getIntent().getStringExtra("type");
        email = getIntent().getStringExtra("email");
        code = getIntent().getStringExtra("code");
        invitedCode = getIntent().getStringExtra("invitedCode");
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }


    public boolean checkoutPassword(String password) {
        boolean flag = false;
        try {
//            Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");//密码为6位纯字母或字母+数字
            Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9]+$");//密码为6位纯字母或字母+数字
            Matcher matcher = pattern.matcher(password);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        ToastUtils.shortToast(toastMessage);
    }

    @Override
    public void getSuccess(EmailLoginDao dao, String password) {
        if(type.equals(Constant.LOGIN_MAIL_LOGIN)){
            //登录不校验type
            //保存用户信息
            SharedPrefsHelper.getInstance().saveSignPassword(password);
            SharedPrefsHelper.getInstance().saveToken(dao.getToken());
            UserDao.delete(this,null,null);
            ContentValues cv = new ContentValues();
            cv.put("username", dao.getUserName());
            cv.put("avatar", "");
            cv.put("email", email);
            cv.put("uid", dao.getId());
            cv.put("fuid", "");
            cv.put("mobile", "");
            UserDao.insert(this, cv);
            HomeActivity.actionStart(SettingPassWordActivity.this);
            closeLoginView();
        }else {
            String type = dao.getType();
            if(type==null){
                //保存密碼
                //設置登陸狀態
                //保存用户信息
                SharedPrefsHelper.getInstance().saveSignPassword(password);
                SharedPrefsHelper.getInstance().saveToken(dao.getUserToken());
                ContentValues cv = new ContentValues();
                cv.put("username", dao.getUserName());
                cv.put("avatar", "");
                cv.put("email", email);
                cv.put("uid", dao.getId());
                cv.put("fuid", "");
                cv.put("mobile", "");
                UserDao.insert(this, cv);
                HomeActivity.actionStart(SettingPassWordActivity.this);
                closeLoginView();
            }else if (type.equals("-2")) {
                //邀请码无效
                NormalErrorDialog dialog = NormalErrorDialog.newInstance(R.string.match_invited_error, R.mipmap.icon_normal_no, R.color.color_E12828);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
            }else if(type.equals("1")){
                //已注册
                NormalErrorDialog dialog = NormalErrorDialog.newInstance(R.string.match_mailbox_error, R.mipmap.icon_normal_no, R.color.color_E12828);
                dialog.setTheme(R.style.PaddingScreen);
                dialog.setGravity(Gravity.CENTER);
                dialog.show(getSupportFragmentManager(), "edit");
            }
        }
    }

    @Override
    public void setPresenter(SettingPasswordContract.SettingPasswordPresenter presenter) {
        this.presenter = presenter;
    }

    void closeLoginView(){
        if (ImportActivity.instance != null) {
            ImportActivity.instance.finish();
        }
        if (LoginActivity.instance != null) {
            LoginActivity.instance.finish();
        }
        if (CreateActivity.instance != null) {
            CreateActivity.instance.finish();
        }
        if (SettingMobileCodeActivity.instance != null) {
            SettingMobileCodeActivity.instance.finish();
        }
        if (InvitationActivity.instance != null) {
            InvitationActivity.instance.finish();
        }
        if (LoginTypeActivity.instance != null) {
            LoginTypeActivity.instance.finish();
        }
        if (EmailActivity.instance != null) {
            EmailActivity.instance.finish();
        }
        finish();
    }
}
