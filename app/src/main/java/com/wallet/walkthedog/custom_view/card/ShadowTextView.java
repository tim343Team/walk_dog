package com.wallet.walkthedog.custom_view.card;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowTextView extends androidx.appcompat.widget.AppCompatTextView{
    public ShadowTextView(Context context) {
        super(context);
    }

    public ShadowTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
    }

    public ShadowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
    }

}
