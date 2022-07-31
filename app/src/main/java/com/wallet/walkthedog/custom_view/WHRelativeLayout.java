package com.wallet.walkthedog.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class WHRelativeLayout extends RelativeLayout {
    public WHRelativeLayout(Context context) {
        super(context);
    }

    public WHRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WHRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WHRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
