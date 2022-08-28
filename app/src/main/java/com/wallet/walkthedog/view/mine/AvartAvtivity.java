package com.wallet.walkthedog.view.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wallet.walkthedog.R;
import com.wallet.walkthedog.bus_event.MessageAvart;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import tim.com.libnetwork.base.BaseActivity;
import tim.com.libnetwork.view.clip_image.ClipImageLayout;

/**
 * ${description}
 *
 * @author weiqiliu
 * @version 1.0 2020/5/14
 */
public class AvartAvtivity extends BaseActivity {
    private static final String WALKDOG = "/walk_avart";
    @BindView(R.id.button)
    ImageView button;
    @BindView(R.id.clipImageLayout)
    ClipImageLayout mClipImageLayout;
    private String filePath;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.button)
    void enter() {
        button.setEnabled(false);
        button.setFocusable(false);
        Bitmap bitmap = mClipImageLayout.clip();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = format.format(date) + ".png";
        File fileFolder = new File(Environment.getExternalStorageDirectory() + WALKDOG);

        if (!fileFolder.exists()) {
            fileFolder.mkdir();
        }
        File jpgFile = new File(fileFolder, filename);
        try {
            FileOutputStream out = new FileOutputStream(jpgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();

            //上传event
            MessageAvart uploadEevent = new MessageAvart(jpgFile.getAbsolutePath());
            EventBus.getDefault().post(uploadEevent);
            finish();
        } catch (FileNotFoundException e) {
            button.setEnabled(true);
            button.setFocusable(true);
            e.printStackTrace();
        } catch (IOException e) {
            button.setEnabled(true);
            button.setFocusable(true);
            e.printStackTrace();
        }
    }

    public static void actionStart(Context context, String filePath) {
        Intent intent = new Intent(context, AvartAvtivity.class);
        intent.putExtra("filePath", filePath);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_user_avart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存
        Glide.with(this).load(filePath).apply(options).into(mClipImageLayout.setImageView());
    }

    @Override
    protected void obtainData() {
        filePath = getIntent().getStringExtra("filePath");
    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

}
