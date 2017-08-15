package com.cardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by wujun on 2017/8/15.
 * 卡券
 *  继承LinearLayout／RelativeLayout，因考虑到实际项目中，需要放复杂的布局，为了减少布局嵌套，决定使用RelativeLayout
 * @author madreain
 * @desc
 */

public class CardView extends RelativeLayout {

    Paint mPaint;

    int cirlceColor;
    //圆半径
    int radius = 10;
    //圆间距
    int gap = 8;
    //圆数量
    int circleNum;
    int mheight;
    //圆数量整除的余数 然后两边对半分
    private float remain;

    public CardView(Context context) {
        super(context);
        init();
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        init();
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeArray(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        remain = ((w - gap) % (radius * 2 + gap)) / 2;
        circleNum = (w - gap) / (radius * 2 + gap);
        mheight = h;
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardView);
        cirlceColor = typedArray.getColor(R.styleable.CardView_cirlceColor, Color.WHITE);
        radius = typedArray.getInteger(R.styleable.CardView_radius, 10);
        gap = typedArray.getInteger(R.styleable.CardView_gap, 8);
        typedArray.recycle();
    }

    private void init() {
        mPaint = new Paint();
        //实际项目中根据背景来设置颜色
        mPaint.setColor(cirlceColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + remain + ((gap + radius * 2) * i);
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, mheight, radius, mPaint);
        }
    }
}
