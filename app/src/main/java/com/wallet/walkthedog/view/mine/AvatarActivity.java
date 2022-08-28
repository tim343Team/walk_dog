package com.wallet.walkthedog.view.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.GlobalConstant;
import com.wallet.walkthedog.app.UrlFactory;
import com.wallet.walkthedog.bus_event.MessageAvart;
import com.wallet.walkthedog.dao.UserInfoDao;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.data.RemoteDataSource;
import com.wallet.walkthedog.dialog.AddFriendDialog;
import com.wallet.walkthedog.dialog.AvatarDialog;
import com.wallet.walkthedog.even.UpdateHomeData;
import com.wallet.walkthedog.even.UpdateMailData;
import com.wallet.walkthedog.net.GsonWalkDogCallBack;
import com.wallet.walkthedog.net.RemoteData;
import com.wallet.walkthedog.sp.SafeGet;
import com.wallet.walkthedog.sp.SharedPrefsHelper;
import com.wallet.walkthedog.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.network.okhttp.WonderfulOkhttpUtils;
import tim.com.libnetwork.utils.WonderfulFileUtils;
import tim.com.libnetwork.utils.WonderfulUriUtils;

public class AvatarActivity extends BaseActivity {
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.imageView)
    ImageView imageView;

    private File imageFile;
    private File imageFileAlbum;
    private String filename = "userAvart.jpg";
    private String albumname = "userAlbum.jpg";
    private Uri imageUri;
    private String avatarFilePath;


    @OnClick(R.id.tv_edit)
    void uploadAvatar() {
        AvatarDialog dialog = AvatarDialog.newInstance();
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setCallback(new AvatarDialog.OperateCallback() {
            @Override
            public void callback(int type) {
                //0相机 1相册
                if (type == 0) {
                    startCamera();
                } else if (type == 1) {
                    chooseFromAlbum();
                }
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_avatar;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        imageFile = WonderfulFileUtils.getCacheSaveFile(this, filename);
        imageFileAlbum = WonderfulFileUtils.getCacheSaveFile(this, albumname);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView iv = findViewById(R.id.imageView);

        SharedPrefsHelper.getInstance().AsyncGetUserInfo().onGet(new SafeGet.SafeCall<UserInfoDao>() {
            @Override
            public void call(UserInfoDao userinfo) {
                Glide.with(iv).load(userinfo.getChatHead())
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv);
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageAvart message) {
        try {
            //上传
            File file = new File(message.getPath());
            avatarFilePath = message.getPath();
            if (file.exists()) {
                //上传图片
                remoteUpload(file.getName(),file);
            } else {
                ToastUtils.shortToast(AvatarActivity.this,R.string.library_file_exception);
            }
            Glide.with(imageView).load(avatarFilePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalConstant.TAKE_PHOTO:
                AvartAvtivity.actionStart(this, imageFile.getPath());
                break;
            case GlobalConstant.CHOOSE_ALBUM:
                if (resultCode != RESULT_OK) {
                    return;
                }
                Uri imageUri = data.getData();
                if (Build.VERSION.SDK_INT >= 19) {
                    imageFileAlbum = WonderfulUriUtils.getUriFromKitKat(this, imageUri);
                } else {
                    imageFileAlbum = WonderfulUriUtils.getUriBeforeKitKat(this, imageUri);
                }
                if (imageFileAlbum == null) {
                    Toast.makeText(this, getResources().getString(R.string.library_file_exception), Toast.LENGTH_SHORT).show();
                    return;
                }
                AvartAvtivity.actionStart(this, imageFileAlbum.getPath());
                break;
            default:
        }

    }

    private void startCamera() {
        if (imageFile == null) {
            Toast.makeText(this, getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            return;
        }
        imageUri = WonderfulFileUtils.getUriForFile(this, imageFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, GlobalConstant.TAKE_PHOTO);
    }

    private void chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GlobalConstant.CHOOSE_ALBUM);
    }

    private void remoteUpload(String fileName, File file) {
        RemoteDataSource.getInstance().uploadFile(fileName, file, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                //更新头像
                updateChatHead((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                ToastUtils.shortToast(toastMessage);
            }
        });
    }

    private void updateChatHead(String url) {
        RemoteDataSource.getInstance().updateChatHead(url, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                ToastUtils.shortToast(AvatarActivity.this,R.string.successful);
                //刷新主页，商城页
                EventBus.getDefault().post(new UpdateHomeData());
                EventBus.getDefault().post(new UpdateMailData());
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                ToastUtils.shortToast(toastMessage);
            }
        });
    }
}
