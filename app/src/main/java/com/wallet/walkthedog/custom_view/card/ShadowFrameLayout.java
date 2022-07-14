package com.wallet.walkthedog.custom_view.card;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowFrameLayout extends FrameLayout implements ShadowDrawable.ShadowPadding {
    private final Rect lastShadowPadding = new Rect();

    public ShadowFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
        ShadowDrawable drawable1 = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable1.bindView(this);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShadowDrawable drawable = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable.bindView(this);
        ShadowDrawable drawable1 = new ShadowDrawable.ShadowBuilder(context, attrs).create();
        drawable1.bindView(this);
    }

    @Override
    public void setShadowpadding(int left, int top, int right, int bottom, Integer[] integers) {
        Rect _lastRect = lastShadowPadding;
        setPadding(integers[0] + left - _lastRect.left, integers[1] + top - _lastRect.top, integers[2] + right - _lastRect.right, integers[3] + bottom - _lastRect.bottom);
        lastShadowPadding.left = left;
        lastShadowPadding.right = right;
        lastShadowPadding.top = top;
        lastShadowPadding.bottom = bottom;
    }
}
