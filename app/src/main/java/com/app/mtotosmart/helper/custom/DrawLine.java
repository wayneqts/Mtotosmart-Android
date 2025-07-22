package com.app.mtotosmart.helper.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawLine extends View {
    public Paint mPaint;
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    public DrawLine(Context context) {
        super(context);
        init();
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(5);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(startX, startY, endX, endY, mPaint);
        invalidate();
    }
}
