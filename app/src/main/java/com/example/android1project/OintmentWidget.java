package com.example.android1project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class OintmentWidget extends View implements Serializable {
    private Paint paint;
    private Path path;

    private float mDensity;

    public OintmentWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDensity = getResources().getDisplayMetrics().density;
        path = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorOintment));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20 * mDensity);
    }

    public OintmentWidget(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
        }
        invalidate();
        return false;
    }*/

    public boolean startApplyOintment(float x, float y) {
        path.moveTo(x, y);
        invalidate();
        return false;
    }

    public boolean applyOintment(float x, float y) {
        path.lineTo(x, y);
        invalidate();
        return false;
    }

    public boolean stopApplyOintment() {
        path.close();
        invalidate();
        return false;
    }
}
