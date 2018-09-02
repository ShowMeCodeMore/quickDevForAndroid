package com.sa.all_cui.mix.mineview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by all-cui on 2017/11/21.
 */

public class CircleView extends View {
    Paint mPaint = null;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mPaint.setColor(ContextCompat.getColor(getContext(),android.R.color.holo_orange_light));
        //drawColor在绘制之前设置设置图形底色
//        canvas.drawColor(Color.parseColor("#88880000"));
//        canvas.drawCircle(400,400,200,mPaint);
        //drawColor绘制之后设置半透明蒙版
        // canvas.drawColor(Color.parseColor("#88880000"));
//        canvas.drawArc(100,100,100,100,100,100,true,mPaint);
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL);
        //£ canvas.drawColor(Color.parseColor("#FF880000"));
//        mPaint.setStrokeWidth(50);
//        canvas.drawCircle(500,500,300,mPaint);
//        canvas.drawRect(100,100,400,400,mPaint);
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawPoint(100,100,mPaint);
//        drawPoints(canvas);
//        drawLines(canvas);
//        drawRoundRect(canvas);
        drawArc(canvas);
    }

    @SuppressLint("NewApi")
    private void drawArc(Canvas canvas) {
        mPaint.setStrokeWidth(15);
        canvas.drawArc(200, 200, 800, 800, 0, 270, true, mPaint);
    }

    @SuppressLint("NewApi")
    private void drawRoundRect(Canvas canvas) {
        mPaint.setStrokeWidth(12);
        canvas.drawRoundRect(100, 100, 700, 500, 10, 10, mPaint);
    }

    private void drawLines(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(points, mPaint);
    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(20);
        canvas.drawLine(20, 20, 300, 400, mPaint);
    }

    @SuppressLint("NewApi")
    private void drawOval(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(100, 100, 400, 400, mPaint);
    }

    private void drawPoints(Canvas canvas) {
        mPaint.setStrokeWidth(20);
        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        //count是要绘制坐标的个数8个，也就是说4个点
        canvas.drawPoints(points, 4, 8, mPaint);
    }
}
