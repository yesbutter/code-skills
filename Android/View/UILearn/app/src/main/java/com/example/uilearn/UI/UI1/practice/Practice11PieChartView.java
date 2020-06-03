package com.example.uilearn.UI.UI1.practice;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private RectF mMaxRectF;

    public Practice11PieChartView(Context context) {
        this(context, null);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF(200, 20, 800, 620);
        mMaxRectF = new RectF(180, 0, 780, 600);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        //设置画布颜色
        canvas.drawColor(Color.parseColor("#506E7A"));

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(45);
        canvas.drawText("饼图", 480, 680, mPaint);

        mPaint.setTextSize(25);
        mPaint.setStrokeWidth(2);
        canvas.drawText("Lollipop", 10, 40, mPaint);
        canvas.drawLines(new float[]{110, 40, 280, 40, 280, 40, (float) (480 - 300 * Math.sin(Math.toRadians(30))), (float) (300 - 300 * Math.cos(Math.toRadians(30)))}, mPaint);
        canvas.drawText("KitKat", 60, 610, mPaint);
        canvas.drawLines(new float[]{150, 605, 320, 605, 320, 605, (float) (500 - 300 * Math.sin(Math.toRadians(30))), (float) (320 + 300 * Math.cos(Math.toRadians(30)))}, mPaint);
        canvas.drawText("Marshmallow", 900, 170, mPaint);
        canvas.drawLines(new float[]{(float) (500 + 300 * Math.cos(Math.toRadians(30))), (float) (320 - 300 * Math.sin(Math.toRadians(30))), 820, 165, 820, 165, 900, 165}, mPaint);
        canvas.drawText("Froyo", 900, 330, mPaint);
        canvas.drawLines(new float[]{800, 325, 900, 325}, mPaint);
        canvas.drawText("Gingerbread", 900, 370, mPaint);
        canvas.drawLines(new float[]{(float) (500 + 300 * Math.cos(Math.toRadians(5))), (float) (320 + 300 * Math.sin(Math.toRadians(5))),
                830, (float) (320 + 300 * Math.sin(Math.toRadians(5))) + 10,
                830, (float) (320 + 300 * Math.sin(Math.toRadians(5))) + 10,
                900, 365}, mPaint);
        canvas.drawText("Ice Cream Sandwich", 900, 410, mPaint);
        canvas.drawLines(new float[]{(float) (500 + 300 * Math.cos(Math.toRadians(12))), (float) (320 + 300 * Math.sin(Math.toRadians(12))),
                830, (float) (320 + 300 * Math.sin(Math.toRadians(12))) + 10,
                830, (float) (320 + 300 * Math.sin(Math.toRadians(12))) + 10, 900, 405}, mPaint);
        canvas.drawText("Jelly Bean", 900, 540, mPaint);
        canvas.drawLines(new float[]{(float) (500 + 300 * Math.cos(Math.toRadians(45))), (float) (320 + 300 * Math.sin(Math.toRadians(45))), 800, 540, 800, 540, 900, 535}, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mRectF, -60, 60, true, mPaint);

        mPaint.setColor(Color.parseColor("#8E24AA"));
        canvas.drawArc(mRectF, 2, 5, true, mPaint);

        mPaint.setColor(Color.parseColor("#D7CCC8"));
        canvas.drawArc(mRectF, 9, 5, true, mPaint);

        mPaint.setColor(Color.parseColor("#66BB6A"));
        canvas.drawArc(mRectF, 16, 60, true, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawArc(mRectF, 78, 100, true, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(mMaxRectF, 180, 118, true, mPaint);
    }
}
