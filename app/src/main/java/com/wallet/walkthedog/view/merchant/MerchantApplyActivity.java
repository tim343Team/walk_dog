package com.wallet.walkthedog.view.merchant;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.app.GlobalConstant;
import com.wallet.walkthedog.app.Injection;
import com.wallet.walkthedog.dao.MerchantStatusDao;
import com.wallet.walkthedog.dao.request.MerchantRequest;
import com.wallet.walkthedog.data.DataSource;
import com.wallet.walkthedog.data.RemoteDataSource;
import com.wallet.walkthedog.dialog.HeaderSelectDialogFragment;
import com.wallet.walkthedog.dialog.NormalDialog;
import com.wallet.walkthedog.dialog.NormalErrorDialog;
import com.wallet.walkthedog.untils.ToastUtils;
import com.wallet.walkthedog.view.card.CardPresenter;
import com.wallet.walkthedog.view.card.CardVerifyActivity;
import com.wallet.walkthedog.view.home.HomeActivity;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.utils.WonderfulBitmapUtils;
import tim.com.libnetwork.utils.WonderfulFileUtils;
import tim.com.libnetwork.utils.WonderfulPermissionUtils;
import tim.com.libnetwork.utils.WonderfulUriUtils;

public class MerchantApplyActivity extends BaseActivity implements MerchantContract.MerchantView {
    @BindView(R.id.edit_email)
    EditText tvEmail;
    @BindView(R.id.img)
    ImageView imgProp;

    private String assetImg;
    private MerchantContract.MerchantPresenter presenter;
    private File imageFile;
    private String filename = "idCard.jpg";
    private Uri imageUri;
    /*权限请求Code*/
    private final static int PERMISSION_REQUEST_CODE = 1234;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    @OnClick(R.id.tv_confirm)
    void confirm() {
        if (!checkoutEmail(tvEmail.getText().toString())) {
            Toast.makeText(MerchantApplyActivity.this, R.string.mailbox_address_notice, Toast.LENGTH_SHORT).show();
            return;
        }
        MerchantRequest request = new MerchantRequest();
        request.setEmail(tvEmail.getText().toString());
        request.setAssetImg(assetImg);
        presenter.applyMerchant(request);
    }

    @OnClick(R.id.ll_upload)
    void uploadll() {
        showHeaderSelectDialog();
    }

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MerchantApplyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_merchant_apply;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new MerchantPresenter(Injection.provideTasksRepository(getApplicationContext()), this);//初始化presenter
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[2]) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permissions[3]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void loadData() {

    }

    public boolean checkoutEmail(String email) {
        boolean flag = false;
        try {
            String emailMatcher = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
            Pattern regex = Pattern.compile(emailMatcher);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public void getFail(Integer code, String toastMessage) {
        NormalErrorDialog dialog = NormalErrorDialog.newInstance(toastMessage, R.mipmap.icon_normal_no, R.color.color_E12828);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void cancleSuccess(String toastMessage) {

    }

    @Override
    public void applySuccess(String toastMessage) {
        NormalDialog dialog = NormalDialog.newInstance(toastMessage, R.mipmap.icon_normal);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.CENTER);
        dialog.show(getSupportFragmentManager(), "edit");
        finish();
    }

    @Override
    public void statusSuccess(MerchantStatusDao merchantStatusDao) {

    }

    @Override
    public void setPresenter(MerchantContract.MerchantPresenter presenter) {
        this.presenter = presenter;
    }

    //权限反馈
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                /*PackageManager.PERMISSION_GRANTED  权限被许可*/
                /*PackageManager.PERMISSION_DENIED  没有权限；拒绝访问*/
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MerchantApplyActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MerchantApplyActivity.this, "无法读取内存卡！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MerchantApplyActivity.this, "无法使用相机！", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0 && grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MerchantApplyActivity.this, "无法录制音频！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalConstant.TAKE_PHOTO:
                    try {
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
                        Glide.with(this).load(imageFile).apply(options).into(imgProp);
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        bitmap = WonderfulBitmapUtils.zoomBitmap(bitmap, bitmap.getWidth() / 6, bitmap.getHeight() / 6);
                        WonderfulBitmapUtils.saveBitmapToFile(bitmap, imageFile, 60);
                        String base64Data = WonderfulBitmapUtils.imgToBase64(bitmap);
                        bitmap.recycle();
                        remoteUpload(imageFile.getName(), imageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case GlobalConstant.CHOOSE_ALBUM:
                    if (resultCode != RESULT_OK) {
                        return;
                    }
                    imageUri = data.getData();
                    if (Build.VERSION.SDK_INT >= 19) {
                        imageFile = WonderfulUriUtils.getUriFromKitKat(this, imageUri);
                    } else {
                        imageFile = WonderfulUriUtils.getUriBeforeKitKat(this, imageUri);
                    }
                    if (imageFile == null) {
                        ToastUtils.shortToast(R.string.library_file_exception);
                        return;
                    }
//                    Bitmap bm = null;
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
//                        bm = WonderfulBitmapUtils.zoomBitmap(bitmap, bitmap.getWidth() / 6, bitmap.getHeight() / 6);
//                    } catch (Exception e) {
//                        ToastUtils.shortToast(R.string.library_file_exception);
//                    }
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE); //缓存
                    Glide.with(this).load(imageFile).apply(options).into(imgProp);
                    remoteUpload(imageFile.getName(), imageFile);
                    break;
                default:
            }
        }
    }

    private void toUpload() {
        if (!WonderfulPermissionUtils.isCanUseCamera(this)) {
        } else {
            showHeaderSelectDialog();
        }
    }

    private void showHeaderSelectDialog() {
        HeaderSelectDialogFragment dialog = HeaderSelectDialogFragment.getInstance(MerchantApplyActivity.this);
        dialog.setTheme(R.style.PaddingScreen);
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show(getSupportFragmentManager(), "edit");
        dialog.setOnDeleteListener(new HeaderSelectDialogFragment.OperateCallback() {
            @Override
            public void toTakePhoto() {
                startCamera();
            }

            @Override
            public void toChooseFromAlbum() {
                chooseFromAlbum();
            }
        });
    }

    private void startCamera() {
        imageFile = WonderfulFileUtils.getCacheSaveFile(this, filename);
        if (imageFile == null) {
            ToastUtils.shortToast(R.string.unknown_error);
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
                ToastUtils.shortToast(R.string.successful);
                assetImg = (String) obj;
                hideLoadingPopup();
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                ToastUtils.shortToast(toastMessage);
            }
        });
    }
}
