package com.example.android1project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class HealthBar extends ProgressBar {
    private int mHp = 100;
    private int mMillis = 1000;
    private Handler mHandler = new Handler();

    private float mDensity;

    public HealthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = getResources().getDisplayMetrics().density;
        //this.setBackgroundColor(Color.BLACK);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mHp > 0){
                    mHp--;
                    android.os.SystemClock.sleep(mMillis);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setProgress(mHp);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        paint.setTextSize(15 * mDensity);

        canvas.drawText("HP: " + mHp, (int) (20 * mDensity), getHeight(), paint);
    }

    public void setMillis(int millis) {
        this.mMillis = millis;
    }

    public int getMillis() {
        return mMillis;
    }

    public int getHp() {
        return mHp;
    }

    public void setHp(int hp) {
        if (hp >= 0 && hp <= 100)
            this.mHp = hp;
    }
}
