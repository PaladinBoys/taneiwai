package com.taneiwai.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.taneiwai.app.R;


/**
* 底部导航view
* @author weiTeng
* @since 2015-12-4 17:29:09
* @version v1.0.0
*/
public class TabView extends View {

    private static final String TAG = "TabView";

    private Rect[] mCacheBounds;
    private Rect[] mTextsBounds;
    private Rect[] mDrawableBounds;
    private Rect[] mTouchBounds;

    private String[] mTexts;
    private Drawable[] mNormalDrawables;
    private Drawable[] mSelectedDawables;

    // 竖直方向图片和文字的间隙
    private int mVerticalGap;
    private int mTextSize;
    private int mSingleWidth;
    private int mSingleHeight;

    private int mBorderWidth;
    private int mBorderColor;
    private int mSelectColor;
    private int mNoticeColor = 0xffff0000;

    private int mPointRadius = 10;
    private int mDrawableWidth = 56;
    private Paint mBorderPaint;
    private Paint mPaint;
    private Paint mNoticePaint;

    private int mTouchSlop;

    private boolean mShowNotice;        // 是否显示红点
    private boolean mTouchClear;
    private boolean inTapRegion;        // 是否点击到了
    private boolean[] mPosNotices;

    private int mStartX;
    private int mStartY;
    private int mCurrentIndex;

    private OnTabClickListener mOnTabClickListener;

    public interface OnTabClickListener{
        void onTabClick(int index);
    }

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        String texts = ta.getString(R.styleable.TabView_tab_texts);
        if (texts != null) {
            mTexts = texts.split("\\|");
        }
        mTextSize = ta.getDimensionPixelSize(R.styleable.TabView_tab_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics()));
        mVerticalGap = ta.getDimensionPixelSize(R.styleable.TabView_tab_verticalGap, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
        mBorderWidth = ta.getDimensionPixelSize(R.styleable.TabView_tab_borderWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics()));
        mBorderColor = ta.getColor(R.styleable.TabView_tab_borderColor, getResources().getColor(android.R.color.darker_gray));
        mSelectColor = ta.getColor(R.styleable.TabView_tab_textSelectColor, 0xff0099cc);
        mShowNotice = ta.getBoolean(R.styleable.TabView_tab_showNotice, false);
        ta.recycle();

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mSelectColor);
        mPaint.setTextSize(mTextSize);

        if(mShowNotice){
            mNoticePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mNoticePaint.setColor(mNoticeColor);
            mPosNotices = new boolean[mTexts.length];
        }

        // 图形区域
        if(mDrawableBounds == null || mDrawableBounds.length != mTexts.length){
            mDrawableBounds = new Rect[mTexts.length];
        }
        // 点击区域的矩形
        if(mTouchBounds == null || mTouchBounds.length != mTexts.length){
            mTouchBounds = new Rect[mTexts.length];
        }

        // 计算使触摸的位置更加精确
        int touchSlop;
        if(context == null){
            touchSlop = ViewConfiguration.getTouchSlop();
        }else{
            final ViewConfiguration config = ViewConfiguration.get(context);
            touchSlop = config.getScaledTouchSlop();
        }
        // 最小的滑动距离
        mTouchSlop = touchSlop * touchSlop;
        inTapRegion = false;
    }

    public void setCurrentIndex(int index){
        setCurrentIndex(index, false);
        invalidate();
    }

    public void setCurrentIndex(int index, boolean tiger){
        mCurrentIndex = index;
        if(mOnTabClickListener != null && tiger){
            mOnTabClickListener.onTabClick(index);
        }
    }

    public void setDrawableWidth(int drawableWidth){
        if(mDrawableWidth != drawableWidth){
            mDrawableWidth = drawableWidth;
            invalidate();
        }
    }

    public boolean isShowNotice() {
        return mShowNotice;
    }

    public void showNoticePoint(boolean showNotice) {
        if(mShowNotice != showNotice) {
            mShowNotice = showNotice;
            invalidate();
        }
    }

    public void showNoticePointAtPostion(int postion, boolean toggle){
        if(!mShowNotice){
            return;
        }
        if(postion >= mTexts.length){
            throw new IndexOutOfBoundsException("超出标签的长度");
        }
        mPosNotices[postion] = toggle;
        invalidate();
    }

    public void clearNoticePointOnTouch(boolean tiger){
        mTouchClear = tiger;
    }

    public void setNormalDrawables(int... resId){
        if(resId.length != mTexts.length){
            throw new IllegalArgumentException("图片长度和文字不符");
        }

        if(mNormalDrawables == null){
            mNormalDrawables = new Drawable[mTexts.length];
        }

        for(int x = 0; x < resId.length; x++){
            mNormalDrawables[x] = getResources().getDrawable(resId[x]);
        }
    }

    public void setSelectedDawables(int... resId){
        if(resId.length != mTexts.length){
            throw new IllegalArgumentException("图片长度和文字不符");
        }
        if(mSelectedDawables == null){
            mSelectedDawables = new Drawable[mTexts.length];
        }
        for(int x = 0; x < resId.length; x++){
            mSelectedDawables[x] = getResources().getDrawable(resId[x]);
        }
    }

    public void setTextSize(int textSize){
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    private void setTextSize(int unit, int textSize){
        mPaint.setTextSize(TypedValue.applyDimension(unit, textSize, getResources().getDisplayMetrics()));

        if(mTextSize != textSize){
            mTextSize = textSize;
            requestLayout();
        }
    }

    public void setBorderColor(int borderColor){
        if(mBorderColor != borderColor){
            mBorderColor = borderColor;
            invalidate();
        }
    }

    public void setSelectColor(int selectColor){
        if(mSelectColor != selectColor){
            mSelectColor = selectColor;
            invalidate();
        }
    }

    public void setBorderWidth(int borderWidth){
        mBorderPaint.setStrokeWidth(mBorderWidth);
        if(mBorderWidth != borderWidth){
            mBorderWidth = borderWidth;
            requestLayout();
        }
    }

    private void getDrawableBounds(Rect borderRect, Rect drawableRect, Drawable drawable){

        int horizonGap = (int) ((mSingleWidth - mDrawableWidth) / 2.0f + 0.5);
        drawableRect.left = borderRect.left + horizonGap;
        drawableRect.top = borderRect.top + mVerticalGap;
        drawableRect.right = drawableRect.left + mDrawableWidth;
        drawableRect.bottom = drawableRect.top + mDrawableWidth;

        drawable.setBounds(drawableRect);
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(mCacheBounds == null || mCacheBounds.length != mTexts.length){
            mCacheBounds = new Rect[mTexts.length];
        }

        if(mTextsBounds == null || mTextsBounds.length != mTexts.length){
            mTextsBounds = new Rect[mTexts.length];
        }
        // 设置文字边界
        for(int i = 0; i < mTexts.length; i++){
            String itemText = mTexts[i];
            if(mTextsBounds[i] == null ){
                mTextsBounds[i] = new Rect();
            }
            mPaint.getTextBounds(itemText, 0, itemText.length(), mTextsBounds[i]);
        }

        int width = 0;
        int height = 0;

        // 获取临时的最小宽度和高度
        int tempSingleWidth = 0;
        int tempSingleHeight = 0;
        if(mNormalDrawables != null && mNormalDrawables.length == mTexts.length) {
            if (mDrawableWidth > mTextsBounds[0].width()) {
                tempSingleWidth = mDrawableWidth;
            } else {
                tempSingleWidth = mTextsBounds[0].width();
            }
            tempSingleHeight = mDrawableWidth + mVerticalGap * 3 + mTextsBounds[0].height();
        }

        if(widthMode == MeasureSpec.AT_MOST){
            if(widthSize <= tempSingleWidth * mTexts.length){
                width = tempSingleWidth * mTexts.length;
            }else{
                width = widthSize;
            }
        }else if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;      // 如果尺寸给的偏小可能会显示不全
        }else if(widthMode == MeasureSpec.UNSPECIFIED){
            width = tempSingleWidth * mTexts.length;
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = tempSingleHeight;
        }

        mSingleWidth = width / mTexts.length;
        mSingleHeight = height;

        for(int i = 0; i < mCacheBounds.length; i++){
            if(mCacheBounds[i] == null){
                mCacheBounds[i] = new Rect();
            }
            if(mDrawableBounds[i] == null){
                mDrawableBounds[i] = new Rect();
            }
            Rect rect = mCacheBounds[i];
            rect.left = i * mSingleWidth;
            rect.top = 0;
            rect.right = rect.left + mSingleWidth;
            rect.bottom = mSingleHeight;

            getDrawableBounds(rect, mDrawableBounds[i], mNormalDrawables[i]);
            getDrawableBounds(rect, mDrawableBounds[i], mSelectedDawables[i]);

            if(mTouchBounds[i] == null){
                mTouchBounds[i] = new Rect();
            }
            mTouchBounds[i].left = mDrawableBounds[i].left - mVerticalGap;
            mTouchBounds[i].top = rect.top + mVerticalGap;
            mTouchBounds[i].right = mDrawableBounds[i].right + mVerticalGap;
            mTouchBounds[i].bottom = mDrawableBounds[i].bottom + mVerticalGap + mTextsBounds[i].height();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:

                inTapRegion = true;

                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getX() - mStartX);
                int dy = (int) (event.getY() - mStartY);

                int distance = dx * dx + dy * dy;
                if(distance > mTouchSlop){
                    inTapRegion = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(inTapRegion){
                    int index = mCurrentIndex;
                    for (int i = 0; i < mTouchBounds.length; i++) {
                        Rect touchRect = mTouchBounds[i];
                        // 如果需要更加精确的时候还需加上Y方向的判断，此View只对水平方向进行判断
                        if(mStartX > touchRect.left && mStartX < touchRect.right){
                            index = i;
                            break;
                        }
                    }
                    if(mCurrentIndex != index) {
                        mCurrentIndex = index;
                        if (mOnTabClickListener != null) {
                            mOnTabClickListener.onTabClick(index);
                        }
                        if(mTouchClear){
                            mPosNotices[index] = false;
                        }
                        invalidate();
                    }
                }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画上边的一条线
        Rect rectFirst = mCacheBounds[0];
        Rect rectLast = mCacheBounds[mTexts.length - 1];
        canvas.drawLine(rectFirst.left, rectFirst.top, rectLast.right, rectLast.top, mBorderPaint);
        canvas.drawLine(rectFirst.left, rectFirst.bottom - 1, rectLast.right, rectLast.bottom - 1, mBorderPaint);

        Drawable drawable;
        for(int i = 0; i < mTexts.length; i++) {
            if (i == mCurrentIndex) {
                drawable = mSelectedDawables[i];
                mPaint.setColor(mSelectColor);
            }else{
                drawable = mNormalDrawables[i];
                mPaint.setColor(mBorderColor);
            }
            drawable.draw(canvas);
            int textOffset = (int) ((mCacheBounds[i].height() - mVerticalGap * 1.5f - mDrawableBounds[i].height() + mTextsBounds[i].height()) / 2.0f + 0.5f);
            canvas.drawText(mTexts[i], mCacheBounds[i].left + (mSingleWidth - mTextsBounds[i].width()) / 2, mDrawableBounds[i].bottom + textOffset, mPaint);

            if(mShowNotice && mPosNotices[i]) {
                canvas.drawCircle(mDrawableBounds[i].right, mDrawableBounds[i].top + mPointRadius, mPointRadius, mNoticePaint);
            }
        }
    }
}