package com.example.uilearn.UI.UI1.practice;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class    Practice5DrawOvalView extends View {

    Paint mPaint = new Paint();
    public Practice5DrawOvalView(Context context) {
        super(context);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(10,25,200,250,mPaint);
//        练习内容：使用 canvas.drawOval() 方法画椭圆
    }
}
