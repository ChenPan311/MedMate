package com.example.android1project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;

public class HealthBar extends ProgressBar {
    private int mHp = 101;
    private int mMillis = 1000;
    private boolean mStop = false;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    private float mDensity;

    private Activity activity;

    public HealthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = getResources().getDisplayMetrics().density;

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mHp > 0) {
                    mHp--;
                    setProgress(mHp);
                } else {
                    mStop = true;
                    setProgress(0);
                    showFailDialog();
                }

                if (!mStop) {
                    mHandler.postDelayed(mRunnable, mMillis);
                } else {
                    mHandler.removeCallbacks(mRunnable);
                }
            }
        };

        mHandler.post(mRunnable);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while(mHp > 0 && !mStop) {
                        mHp--;
                        android.os.SystemClock.sleep(mMillis);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                setProgress(mHp);
                            }
                        });
                    if (mHp == 0)
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                showFailDialog();
                            }
                        });
                }
            }
        }).start();*/
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
        else if (hp < 0)
            this.mHp = 0;
        else
            this.mHp = 100;
    }

    public boolean isStopped() {
        return mStop;
    }

    public void stop() {
        mHp++;
        this.mStop = true;
    }

    public void resume() {
        this.mStop = false;
        mHandler.post(mRunnable);
        /*mHp++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mHp > 0 && !mStop) {
                    mHp--;
                    android.os.SystemClock.sleep(mMillis);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setProgress(mHp);
                        }
                    });
                }
                if (mHp == 0)
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            showFailDialog();
                        }
                    });
            }
        }).start();*/
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    void showFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_fail,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        view.findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Option1Activity.class);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}
