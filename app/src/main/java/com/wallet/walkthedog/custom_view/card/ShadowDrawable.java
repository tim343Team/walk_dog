/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wallet.walkthedog.custom_view.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wallet.walkthedog.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A rounded rectangle drawable which also includes a shadow around.
 */
public class ShadowDrawable extends Drawable {
    // used to calculate content padding
    private static final double COS_45 = Math.cos(Math.toRadians(45));

    private static final float SHADOW_MULTIPLIER = 1.5f;

    private final int mInsetShadow; // extra shadow to avoid gaps between card and shadow

    /*
     * This helper is set by CardView implementations.
     * <p>
     * Prior to API 17, canvas.drawRoundRect is expensive; which is why we need this interface
     * to draw efficient rounded rectangles before 17.
     * */
    static RoundRectHelper sRoundRectHelper = new RoundRectHelper() {
        @Override
        public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                  Paint paint) {
            canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
        }
    };
    private static final PaddingHolder paddingHolder = new PaddingHolder();

    private static class PaddingHolder {
        private final Map<Integer, Rect> idToShadowPadding = new HashMap<>();

        public void setOriginPadding(View view, Rect rect) {
            idToShadowPadding.put(view.getId(), rect);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {

                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    view.removeOnAttachStateChangeListener(this);
                    idToShadowPadding.remove(view.getId());
                }
            });
        }
    }

    /**
     * 用它去bindView
     */
    public void bindView(View view) {
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();

        view.setBackground(this);
        Rect shadowPadding = new Rect();
        getMaxShadowAndCornerPadding(shadowPadding);
        if (view instanceof ShadowPadding) {
            ((ShadowPadding) view).setShadowpadding(shadowPadding.left, shadowPadding.top, shadowPadding.right, shadowPadding.bottom, new Integer[]{paddingLeft, paddingTop, paddingRight, paddingBottom});
        } else {
            int id = view.getId();
            if (id == View.NO_ID) {
                throw new RuntimeException("view must has id or implements ShadowPadding");
            }
            Rect lastshadowPadding = paddingHolder.idToShadowPadding.get(id);
            if (lastshadowPadding == null) {
                lastshadowPadding = new Rect();
                paddingHolder.setOriginPadding(view, lastshadowPadding);
            }
            view.setPadding(paddingLeft + shadowPadding.left - lastshadowPadding.left, paddingTop + shadowPadding.top - lastshadowPadding.top, paddingRight + shadowPadding.right - lastshadowPadding.right, paddingBottom + shadowPadding.bottom - lastshadowPadding.bottom);
            lastshadowPadding.left = shadowPadding.left;
            lastshadowPadding.top = shadowPadding.top;
            lastshadowPadding.right = shadowPadding.right;
            lastshadowPadding.bottom = shadowPadding.bottom;
        }
    }

    public interface ShadowPadding {
        void setShadowpadding(int left, int top, int right, int bottom, Integer[] integers);
    }

    private final Paint mPaint;

    private final Paint mCornerShadowPaint;

    private final Paint mEdgeShadowPaint;

    private final RectF mCardBounds;

    private float mCornerRadius;

    private Path mCornerShadowPath;

    // actual value set by developer
    private float mRawMaxShadowSize;

    // multiplied value to account for shadow offset
    private float mShadowSize;

    // actual value set by developer
    private float mRawShadowSize;

    private int[] mBackground;

    private boolean mDirty = true;

    private final int mShadowStartColor;

    private final int mShadowEndColor;

    private boolean mAddPaddingForCorners = true;

    /**
     * If shadow size is set to a value above max shadow, we print a warning
     */
    private boolean mPrintedShadowClipWarning = false;

    ShadowDrawable(int startColor, int endColor, int[] backgroundColors, float radius,
                   float shadowSize) {
        if (radius <= 0) {
            radius = 1F;
        }
        if (backgroundColors == null || backgroundColors.length == 0) {
            backgroundColors = new int[]{Color.TRANSPARENT};
        }
        mShadowStartColor = startColor;
        mShadowEndColor = endColor;
        mInsetShadow = 3;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        setBackground(backgroundColors);
        mCornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mCornerShadowPaint.setStyle(Paint.Style.FILL);
        mCornerRadius = (int) (radius + .5f);
        mCardBounds = new RectF();
        mEdgeShadowPaint = new Paint(mCornerShadowPaint);
        mEdgeShadowPaint.setAntiAlias(false);
        setShadowSize(shadowSize, shadowSize);
    }

    private void setBackground(int[] color) {
        mBackground = color;
    }

    /**
     * Casts the value to an even integer.
     */
    private int toEven(float value) {
        int i = (int) (value + .5f);
        if (i % 2 == 1) {
            return i - 1;
        }
        return i;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        mCornerShadowPaint.setAlpha(alpha);
        mEdgeShadowPaint.setAlpha(alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mDirty = true;
    }

    private void setShadowSize(float shadowSize, float maxShadowSize) {
        if (shadowSize < 0f) {
            throw new IllegalArgumentException("Invalid shadow size " + shadowSize
                    + ". Must be >= 0");
        }
        if (maxShadowSize < 0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + maxShadowSize
                    + ". Must be >= 0");
        }
        shadowSize = toEven(shadowSize);
        maxShadowSize = toEven(maxShadowSize);
        if (shadowSize > maxShadowSize) {
            shadowSize = maxShadowSize;
            if (!mPrintedShadowClipWarning) {
                mPrintedShadowClipWarning = true;
            }
        }
        if (mRawShadowSize == shadowSize && mRawMaxShadowSize == maxShadowSize) {
            return;
        }
        mRawShadowSize = shadowSize;
        mRawMaxShadowSize = maxShadowSize;
        mShadowSize = (int) (shadowSize * SHADOW_MULTIPLIER + mInsetShadow + .5f);
        mDirty = true;
        invalidateSelf();
    }

    @Override
    public boolean getPadding(Rect padding) {
        int vOffset = (int) Math.ceil(calculateVerticalPadding(mRawMaxShadowSize, mCornerRadius,
                mAddPaddingForCorners));
        int hOffset = (int) Math.ceil(calculateHorizontalPadding(mRawMaxShadowSize, mCornerRadius,
                mAddPaddingForCorners));
        padding.set(hOffset + padding.left, vOffset + padding.top, hOffset + padding.right, vOffset + padding.bottom);
        return true;
    }

    static float calculateVerticalPadding(float maxShadowSize, float cornerRadius,
                                          boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize * SHADOW_MULTIPLIER;
        }
    }

    static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius,
                                            boolean addPaddingForCorners) {
        if (addPaddingForCorners) {
            return (float) (maxShadowSize + (1 - COS_45) * cornerRadius);
        } else {
            return maxShadowSize;
        }
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
//        final int newColor = mBackground.getColorForState(stateSet, mBackground.getDefaultColor());
//        if (mPaint.getColor() == newColor) {
//            return false;
//        }
        //  mPaint.setColor(newColor);
        mDirty = true;
        invalidateSelf();
        return true;
    }

    @Override
    public boolean isStateful() {
        return super.isStateful();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mDirty) {
            buildComponents(getBounds());
            mDirty = false;
        }
        canvas.translate(0, mRawShadowSize / 2);
        drawShadow(canvas);
        canvas.translate(0, -mRawShadowSize / 2);
        sRoundRectHelper.drawRoundRect(canvas, mCardBounds, mCornerRadius, mPaint);
    }

    private void drawShadow(Canvas canvas) {
        final float edgeShadowTop = -mCornerRadius - mShadowSize;
        final float inset = mCornerRadius + mInsetShadow + mRawShadowSize / 2;
        final boolean drawHorizontalEdges = mCardBounds.width() - 2 * inset > 0;
        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.top + inset);
        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset, -mCornerRadius,
                    mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RB
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.bottom - inset);
        canvas.rotate(180f);
        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset, -mCornerRadius + mShadowSize,
                    mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // LB
        saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.bottom - inset);
        canvas.rotate(270f);
        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RT
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.top + inset);
        canvas.rotate(90f);
        canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint);
        }
        canvas.restoreToCount(saved);
    }


    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-mShadowSize, -mShadowSize);

        if (mBackground.length == 1) {
            mPaint.setColor(mBackground[0]);
        } else {
            LinearGradient linearGradient = new LinearGradient(0, mCardBounds.height() / 2, mCardBounds.width(), mCardBounds.height() / 2, mBackground, null, Shader.TileMode.MIRROR);
            mPaint.setShader(linearGradient);
        }


        if (mCornerShadowPath == null) {
            mCornerShadowPath = new Path();
        } else {
            mCornerShadowPath.reset();
        }
        mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        mCornerShadowPath.moveTo(-mCornerRadius, 0);
        mCornerShadowPath.rLineTo(-mShadowSize, 0);
        // outer arc
        mCornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
        // inner arc
        mCornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
        mCornerShadowPath.close();
        float startRatio = mCornerRadius / (mCornerRadius + mShadowSize);
        mCornerShadowPaint.setShader(new RadialGradient(0, 0, mCornerRadius + mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
                new float[]{0f, startRatio, 1f},
                Shader.TileMode.CLAMP));

        // we offset the content shadowSize/2 pixels up to make it more realistic.
        // this is why edge shadow shader has some extra space
        // When drawing bottom edge shadow, we use that extra space.
        mEdgeShadowPaint.setShader(new LinearGradient(0, -mCornerRadius + mShadowSize, 0,
                -mCornerRadius - mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));
        mEdgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect bounds) {
        // Card is offset SHADOW_MULTIPLIER * maxShadowSize to account for the shadow shift.
        // We could have different top-bottom offsets to avoid extra gap above but in that case
        // center aligning Views inside the CardView would be problematic.
        final float verticalOffset = mRawMaxShadowSize * SHADOW_MULTIPLIER;
        mCardBounds.set(bounds.left + mRawMaxShadowSize, bounds.top + verticalOffset,
                bounds.right - mRawMaxShadowSize, bounds.bottom - verticalOffset);
        buildShadowCorners();
    }

    float getCornerRadius() {
        return mCornerRadius;
    }

    void getMaxShadowAndCornerPadding(Rect into) {
        getPadding(into);
    }

    void setShadowSize(float size) {
        setShadowSize(size, mRawMaxShadowSize);
    }

    void setMaxShadowSize(float size) {
        setShadowSize(mRawShadowSize, size);
    }

    float getShadowSize() {
        return mRawShadowSize;
    }

    float getMaxShadowSize() {
        return mRawMaxShadowSize;
    }

    float getMinWidth() {
        final float content = 2
                * Math.max(mRawMaxShadowSize, mCornerRadius + mInsetShadow + mRawMaxShadowSize / 2);
        return content + (mRawMaxShadowSize + mInsetShadow) * 2;
    }

    float getMinHeight() {
        final float content = 2 * Math.max(mRawMaxShadowSize, mCornerRadius + mInsetShadow
                + mRawMaxShadowSize * SHADOW_MULTIPLIER / 2);
        return content + (mRawMaxShadowSize * SHADOW_MULTIPLIER + mInsetShadow) * 2;
    }


    interface RoundRectHelper {
        void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint);
    }

    public static class ShadowBuilder {

        private int cardBackgroundColor;
        private int startColor;
        private int endColor;
        private float cardCornerRadius;
        private float cardElevation;
        private int[] mGradientColor;

        public ShadowBuilder() {

        }

        public ShadowBuilder(Context context, AttributeSet attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowBuilder);
            cardBackgroundColor = ta.getColor(R.styleable.ShadowBuilder_cardBackgroundColor, Color.TRANSPARENT);
            startColor = ta.getColor(R.styleable.ShadowBuilder_shadowStartColor, Color.TRANSPARENT);
            String gradientColorStr = ta.getString(R.styleable.ShadowBuilder_shadowGradientColor);
            if (gradientColorStr != null) {
                String[] colors = gradientColorStr.split(",");
                mGradientColor = new int[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    mGradientColor[i] = Color.parseColor(colors[i]);
                }
            }

            endColor = ta.getColor(R.styleable.ShadowBuilder_shadowEndColor, startColor);
            cardCornerRadius = ta.getDimension(R.styleable.ShadowBuilder_cardCornerRadius, 0F);
            cardElevation = ta.getDimension(R.styleable.ShadowBuilder_cardElevation, 0F);
            ta.recycle();
        }

        public ShadowBuilder setCardBackgroundColor(int cardBackgroundColor) {
            this.cardBackgroundColor = cardBackgroundColor;
            return this;
        }

        public ShadowBuilder setStartColor(int startColor) {
            this.startColor = startColor;
            return this;
        }

        public ShadowBuilder setEndColor(int endColor) {
            this.endColor = endColor;
            return this;
        }

        public ShadowBuilder setCardCornerRadius(float cardCornerRadius) {
            this.cardCornerRadius = cardCornerRadius;
            return this;
        }

        public ShadowBuilder setCardElevation(float cardElevation) {
            this.cardElevation = cardElevation;
            return this;
        }

        public ShadowBuilder setmGradientColor(int[] mGradientColor) {
            this.mGradientColor = mGradientColor;
            return this;
        }

        public ShadowDrawable create() {
            if (mGradientColor == null || mGradientColor.length == 0) {
                mGradientColor = new int[]{cardBackgroundColor};
            }
            return new ShadowDrawable(startColor, endColor, mGradientColor, cardCornerRadius, cardElevation);
        }


    }
}
