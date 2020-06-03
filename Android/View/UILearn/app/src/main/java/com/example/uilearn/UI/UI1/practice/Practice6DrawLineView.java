package com.example.uilearn.UI.UI1.practice;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;

import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Practice6DrawLineView extends View {
    Paint mPaint = new Paint();
    public Practice6DrawLineView(Context context) {
        super(context);
    }

    public Practice6DrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice6DrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(25f);
        canvas.drawLine(10,10,10,300,mPaint);
        canvas.drawLine(10,300,400,400,mPaint);
//        练习内容：使用 canvas.drawLine() 方法画直线
    }
}
