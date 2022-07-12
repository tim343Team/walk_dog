package com.wallet.walkthedog.custom_view.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowFrameLayout extends FrameLayout {
    public ShadowFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
    }

}
