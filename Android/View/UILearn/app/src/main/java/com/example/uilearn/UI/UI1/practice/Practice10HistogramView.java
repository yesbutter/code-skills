package com.example.uilearn.UI.UI1.practice;

import android.content.Context;
import android.graphics.Canvas;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {
    Paint mPaint = new Paint();

    public Practice10HistogramView(Context context) {
        super(context);
    }

    private float[] mPoints = {100, 20, 100, 501, 100, 501, 900, 501};

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#506E7A"));

        mPaint.setStrokeWidth(0f);
        mPaint.setColor(Color.WHITE);
        canvas.drawLines(mPoints,mPaint);

        mPaint.setTextSize(50);
        canvas.drawText("直方图",450,670,mPaint);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        mPaint.setTextSize(25);
        canvas.drawText("Froyo", 140, 530, mPaint);
        canvas.drawText("GB", 260, 530, mPaint);
        canvas.drawText("ICS", 355, 530, mPaint);
        canvas.drawText("JB", 460, 530, mPaint);
        canvas.drawText("KitKat", 540, 530, mPaint);
        canvas.drawText("L", 670, 530, mPaint);
        canvas.drawText("M", 770, 530, mPaint);

        canvas.drawRect(140, 490, 220, 500, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(240, 470, 320, 500, mPaint);
        canvas.drawRect(340, 470, 420, 500, mPaint);
        canvas.drawRect(440, 270, 520, 500, mPaint);
        canvas.drawRect(540, 200, 620, 500, mPaint);
        canvas.drawRect(640, 150, 720, 500, mPaint);
        canvas.drawRect(740, 290, 820, 500, mPaint);
    }
}