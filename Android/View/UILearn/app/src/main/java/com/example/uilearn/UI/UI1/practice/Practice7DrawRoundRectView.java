package com.example.uilearn.UI.UI1.practice;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class Practice7DrawRoundRectView extends View {

    private Paint mPaint = new Paint();

    public Practice7DrawRoundRectView(Context context) {
        super(context);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(10,10,400,400,100,100,mPaint);
//        练习内容：使用 canvas.drawRoundRect() 方法画圆角矩形
    }
}
