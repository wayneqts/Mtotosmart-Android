package com.app.mtotosmart.helper.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ConnectionView extends View {
    private Paint paint = new Paint();
    private List<Line> lines = new ArrayList<>();
    private PointF currentStart, currentEnd;
    private boolean isDrawing = false;

    public ConnectionView(Context context) {
        super(context);
        init();
    }

    public ConnectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.parseColor("#47AAF4"));
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
    }

    public void startLine(float x, float y) {
        currentStart = new PointF(x, y);
        currentEnd = new PointF(x, y);
        isDrawing = true;
        invalidate();
    }

    public void updateLine(float x, float y) {
        if (isDrawing) {
            currentEnd.x = x;
            currentEnd.y = y;
            invalidate();
        }
    }

    public void endLine(float x, float y, boolean addLine) {
        if (isDrawing && addLine) {
            lines.add(new Line(currentStart, new PointF(x, y)));
        }
        isDrawing = false;
        invalidate();
    }

    public void resetLines() {
        lines.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Line line : lines) {
            canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, paint);
        }
        if (isDrawing && currentStart != null && currentEnd != null) {
            canvas.drawLine(currentStart.x, currentStart.y, currentEnd.x, currentEnd.y, paint);
        }
    }

    private static class Line {
        PointF start, end;

        Line(PointF s, PointF e) {
            start = s;
            end = e;
        }
    }
}







