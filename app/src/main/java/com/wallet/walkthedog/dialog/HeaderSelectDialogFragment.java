package com.wallet.walkthedog.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.walkthedog.R;

import java.lang.reflect.Method;

import butterknife.BindView;
import tim.com.libnetwork.base.BaseDialogFragment;

/**
 * Created by Administrator on 2018/3/9.
 */

public class HeaderSelectDialogFragment extends BaseDialogFragment {
    @BindView(R.id.tvTakePhone)
    TextView tvTakePhone;
    @BindView(R.id.llTakePhoto)
    LinearLayout llTakePhoto;
    @BindView(R.id.tvAlbum)
    TextView tvAlbum;
    @BindView(R.id.llChoseAlbum)
    LinearLayout llChoseAlbum;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.line_quxiao)
    LinearLayout line_quxiao;
    private static Context context1;
    public static HeaderSelectDialogFragment getInstance(Context context) {
        HeaderSelectDialogFragment fragment = new HeaderSelectDialogFragment();
        context1=context;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (!(getActivity() instanceof OperateCallback)) {
//            throw new RuntimeException("The Activity which fragment is located must implement the OperateCallback interface!");
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_header_select;
    }

    @Override
    protected void prepareView(View view) {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void initView() {
        llTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((OperateCallback) getActivity()).toTakePhoto();
                }catch (Exception e){
                    callback.toTakePhoto();
                }
                dismiss();
            }
        });
        llChoseAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((OperateCallback) getActivity()).toChooseFromAlbum();
                }catch (Exception e){
                    callback.toChooseFromAlbum();
                }
                dismiss();
            }
        });
        line_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    OperateCallback callback;

    public void setOnDeleteListener(OperateCallback callback) {
        this.callback = callback;
    }

    public interface OperateCallback {
        void toTakePhoto();

        void toChooseFromAlbum();
    }
}
