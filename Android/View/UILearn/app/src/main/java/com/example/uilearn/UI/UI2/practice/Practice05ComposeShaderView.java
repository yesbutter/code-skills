package com.example.uilearn.UI.UI2.practice;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.uilearn.R;

public class Practice05ComposeShaderView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice05ComposeShaderView(Context context) {
        super(context);
    }

    public Practice05ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
//        setLayerType(LAYER_TYPE_SOFTWARE, null); // 硬件加速下 ComposeShader 不能使用两个同类型的 Shader
        Shader shader1 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.batman), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Shader shader2 = new BitmapShader(BitmapFactory.decodeResource(getResources(),R.drawable.batman_logo), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(new ComposeShader(shader1,shader2, PorterDuff.Mode.DST_IN));
        // 用 Paint.setShader(shader) 设置一个 ComposeShader
        // Shader 1: BitmapShader 图片：R.drawable.batman
        // Shader 2: BitmapShader 图片：R.drawable.batman_logo
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(400, 400, 400, paint);
    }
}
