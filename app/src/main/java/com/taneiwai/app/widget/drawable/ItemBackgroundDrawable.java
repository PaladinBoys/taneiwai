package com.taneiwai.app.widget.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by weiTeng on 15/12/19.
 */
public class ItemBackgroundDrawable extends Drawable {

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mButtom;

    private int mTopLeftRadius;
    private int mTopRightRadius;
    private int mButtomLeftRadius;
    private int mButtomRightRadius;

    private int mColor = 0xffffffff;
    private int mStrokeWidth;
    private int mStrokeColor;

    private Paint mPaint;
    private Paint mStrokePaint;

    private Path mPath;

    public ItemBackgroundDrawable(int topLeftRadius, int radiusOther, int color, int strokeWidth, int strokeColor){
        this.mTopLeftRadius = topLeftRadius;
        mTopRightRadius = mButtomLeftRadius = mButtomRightRadius = radiusOther;
        this.mColor = color;
        this.mStrokeWidth = strokeWidth;
        this.mStrokeColor = strokeColor;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(mStrokeWidth > 0) {
            mStrokePaint.setColor(mStrokeColor);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth(strokeWidth);
            mStrokePaint.setStrokeJoin(Paint.Join.MITER);
        }
    }

    public ItemBackgroundDrawable(int topLeftRadius, int topRightRadius, int buttomLeftRadius, int buttomRightRadius) {
        mTopLeftRadius = topLeftRadius;
        mTopRightRadius = topRightRadius;
        mButtomLeftRadius = buttomLeftRadius;
        mButtomRightRadius = buttomRightRadius;
    }

    public void setStrokeWidth(int strokeWidth){
        this.mStrokeWidth = strokeWidth;
    }

    public void setStrokeColor(int strokeColor){
        this.mStrokeColor = strokeColor;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mLeft = left;
        mTop = top;
        mRight = right;
        mButtom = bottom;

        mPath = new Path();
        mPath.moveTo(mLeft + mTopLeftRadius, top);
        mPath.lineTo(mRight - mTopRightRadius, top);

        mPath.arcTo(new RectF(mRight - mTopRightRadius * 2, mTop, mRight, mTop + mTopRightRadius * 2), 270, 90);
        mPath.lineTo(mRight, mButtom - mButtomRightRadius);

        mPath.arcTo(new RectF(mRight - mButtomRightRadius * 2, mButtom - mButtomRightRadius * 2, mRight, mButtom), 0, 90);
        mPath.lineTo(mLeft + mTopLeftRadius * 0.5f + mButtomLeftRadius, mButtom);

        mPath.arcTo(new RectF(mLeft + mTopLeftRadius * 0.5f, mButtom - mButtomLeftRadius * 2, mLeft + mTopLeftRadius * 0.5f + mButtomLeftRadius * 2, mButtom), 90, 90);
        mPath.lineTo(mLeft + mTopLeftRadius * 0.5f, (float) (mTop + mTopLeftRadius + mTopLeftRadius * Math.sin(Math.PI / 3)));

        mPath.arcTo(new RectF(mLeft, mTop, mLeft + mTopLeftRadius * 2, mTop + mTopLeftRadius * 2), 120, 150);
        mPath.close();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);

        if(mStrokeWidth > 0){
            canvas.drawPath(mPath, mStrokePaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.getColorFilter();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
