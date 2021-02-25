package com.example.utilsandviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.utilsandviews.R;

/**
 * 自定义电池View
 */
public class BatteryVerticalView extends View {

    private int mPower = 100;
    private int orientation;
    private int width;
    private int height;
    private int mColor;

    public BatteryVerticalView(Context context) {
        super(context);
    }

    public BatteryVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        mColor = typedArray.getColor(R.styleable.Battery_batteryColor, 0xFFFFFFFF);
        orientation = typedArray.getInt(R.styleable.Battery_batteryOrientation, 1);
        mPower = typedArray.getInt(R.styleable.Battery_batteryPower, 100);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对View上的內容进行测量后得到的View內容占据的宽度
        width = getMeasuredWidth();
        //对View上的內容进行测量后得到的View內容占据的高度
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawVerticalBattery(canvas);
    }

    /**
     * 绘制垂直电池
     *
     * @param canvas
     */
    private void drawVerticalBattery(Canvas canvas) {
        Paint paint = new Paint();
        //设置外边框颜色
        paint.setColor(getResources().getColor(R.color.color_dianchi));
        paint.setStyle(Paint.Style.STROKE);
        //外框宽度调整（除数越大宽度越小）
        float strokeWidth = height / 35.0f;
        float strokeWidth2 = strokeWidth / 2;
        paint.setStrokeWidth(strokeWidth);
        int headHeight = (int) (strokeWidth + 0.5f);
        RectF rect = new RectF(strokeWidth2, headHeight + strokeWidth2, width - strokeWidth2, height - strokeWidth2);
        canvas.drawRect(rect, paint);
        paint.setStrokeWidth(0);
        float topOffset = (height - headHeight - strokeWidth) * (100 - mPower) / 100.0f;
        RectF rect2 = new RectF(strokeWidth, headHeight + strokeWidth + topOffset, width - strokeWidth, height - strokeWidth);
        paint.setStyle(Paint.Style.FILL);
        //根据电池电量决定电池内矩形电量颜色
        if (mPower < 10) {
            paint.setColor(Color.RED);
        }
        if (mPower >= 10 && mPower < 90) {
            paint.setColor(getResources().getColor(R.color.colorAccent));
        }
        if (mPower >= 90) {
            paint.setColor(Color.GREEN);
        }
        canvas.drawRect(rect2, paint);
        RectF headRect = new RectF(width / 4.0f, 0, width * 0.75f, headHeight);
        //设置电池头颜色
        paint.setColor(getResources().getColor(R.color.color_dianchi));
        canvas.drawRect(headRect, paint);
    }

    /**
     * 设置电池电量
     *
     * @param power
     */
    public void setPower(int power) {
        this.mPower = power;
        if (mPower < 0) {
            mPower = 100;
        }
        invalidate();//刷新VIEW
    }

    /**
     * 设置电池颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.mColor = color;
        invalidate();
    }

    /**
     * 获取电池电量
     *
     * @return
     */
    public int getPower() {
        return mPower;
    }
}