package com.example.android1project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OintmentWidget extends View {
    private ArrayList<Circle> circles = new ArrayList<>();
    private Paint paint;

    private float mDensity;

    public OintmentWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDensity = getResources().getDisplayMetrics().density;

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

        for (Circle circle : circles) {
            paint.setColor(circle.getColor());
            //Log.wtf("XY"," " + circle.getX() + ", " + circle.getY());
            canvas.drawCircle(circle.getX(), circle.getY(), 5 * mDensity, paint);
        }
    }

    public void applyOintment(final float x, final float y) {
        final Circle circle = new Circle(x, y);

        circles.add(circle);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circles.remove(circle);
            }
        }, 750);
        invalidate();
    }

    public void finishApplying() {
        for (final Circle circle : circles) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    circle.setAlpha(0.20f);
                    invalidate();
                }
            }, 100);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    circle.setAlpha(0.15f);
                    invalidate();
                }
            }, 200);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    circle.setAlpha(0.1f);
                    invalidate();
                }
            }, 300);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    circle.setAlpha(0.05f);
                    invalidate();
                }
            }, 400);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    circles.clear();
                    invalidate();
                }
            }, 500);
        }
    }
}
