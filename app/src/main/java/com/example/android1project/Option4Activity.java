package com.example.android1project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Option4Activity extends AppCompatActivity {
    private int difficulty;

    private float mDensity;

    private ImageView splinter;
    private ImageView used_band_aid;

    private MedKit mMedKit;

    private HealthBar mHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_4_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView white_bg = findViewById(R.id.white_bg_4);
        final ImageView item1 = findViewById(R.id.item4);
        splinter = findViewById(R.id.bee_splinter);
        used_band_aid = findViewById(R.id.girl2_used_band_aid);


        difficulty = getIntent().getIntExtra("difficulty", 1);
        mHp = findViewById(R.id.hp_bar4z);
        mHp.setActivity(this);
        if (difficulty == 1) {
            mHp.setMillis(1000);
        }
        else if (difficulty == 2)
            mHp.setMillis(500);
        else if (difficulty == 3)
            mHp.setMillis(250);

        mMedKit = findViewById(R.id.first_aid_kit_4);
        mMedKit.setItemId(item1.getId());
        mMedKit.setOnClickListener(mMedKit);

        ImageView defi = mMedKit.mLayout.findViewById(R.id.defibrillator);
        defi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1.setVisibility(View.GONE);
                mMedKit.setIsTweezers(false);
                mMedKit.setIsBandAid(false);
                mMedKit.setIsOintment(false);
                mMedKit.setIsEpipen(false);
                mMedKit.setIsDefibrillator(true);
                mMedKit.setIsPen(false);
                mMedKit.DismissWindow();

                makeDeviceVibrate(1000);
                AlphaAnimation anim = new AlphaAnimation(1f, 0f);
                anim.setDuration(1000);
                white_bg.startAnimation(anim);
                mHp.setHp(0);
            }
        });

        final ImageButton play_pause_btn = findViewById(R.id.play_pause_btn_4);
        play_pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_pause_btn.setAlpha(0.25f);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(50);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                scaleAnimation.setRepeatCount(1);
                play_pause_btn.startAnimation(scaleAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        play_pause_btn.setAlpha(1f);
                    }
                }, 50);

                mHp.stop();
                showPausedDialog();
            }
        });

        item1.setOnTouchListener(new View.OnTouchListener() {
            boolean isClosed = false, isOut = false, isBaUsed = false, isEpipenUsed = false;
            RelativeLayout.LayoutParams layoutParams;
            int deltaX = 0, deltaY = 0;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenHeight = displayMetrics.heightPixels;
            int screenWidth = displayMetrics.widthPixels;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (item1.getVisibility() == View.VISIBLE) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            deltaX = x - layoutParams.getMarginStart();
                            deltaY = y - layoutParams.topMargin;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            if (!isOut && mMedKit.isTweezers() && checkCollision(item1, splinter) && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                item1.setY(item1.getY() + (10 * mDensity));
                                item1.invalidate();
                                isClosed = true;
                            } else if (!mMedKit.isTweezers() || !isClosed)
                                isClosed = false;

                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                            layoutParams.topMargin = isClosed ? layoutParams.topMargin : Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);
                            v.setLayoutParams(layoutParams);

                            if (!isOut && isClosed) {
                                splinter.setX(item1.getX() - splinter.getWidth() + (40 * mDensity));
                                //splinter.setY(item1.getY() + item1.getHeight() - (15 * mDensity));
                                splinter.invalidate();
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (!isBaUsed && isOut && mMedKit.isBandAid() && checkCollision(item1, findViewById(R.id.girl_2_wound))) {
                                used_band_aid.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.INVISIBLE);
                                isBaUsed = true;
                            } else if (!isOut && mMedKit.isTweezers() && isClosed) {
                                item1.setVisibility(View.INVISIBLE);
                                splinter.setVisibility(View.GONE);
                                isOut = true;
                                isClosed = false;
                            } else if (!isEpipenUsed && mMedKit.isEpipen() && checkCollision(item1, findViewById(R.id.girl_2_thigh))) {
                                item1.setVisibility(View.INVISIBLE);
                                makeDeviceVibrate(250);
                                isEpipenUsed = true;
                            }
                            if (isOut && isBaUsed && isEpipenUsed) {
                                mHp.stop();
                                showSuccessDialog();
                            }
                            if (checkCollision(item1, mMedKit)) {
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                            }
                            break;
                    }
                    v.requestLayout();
                }
                return true;
            }
        });
    }

    public boolean checkCollision(View tool, View object) {
        Rect R2;
        if (object.getId() == R.id.bee_splinter) {
            R2 = new Rect(object.getRight() - (int) (5 * mDensity), object.getTop(), object.getRight(), object.getBottom());
        } else if (object.getId() == R.id.girl_2_wound) {
            R2 = new Rect(object.getRight() - (int) (2 * mDensity), object.getTop() + (int) (4 * mDensity), object.getRight(), object.getBottom());
        } else {
            R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        }

        if (mMedKit.isEpipen()) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop() + (int) (160 * mDensity), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mMedKit.isTweezers()) {
            Rect R1 = new Rect(tool.getLeft() + (int) (30 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (15 * mDensity), tool.getBottom());
            return R1.intersect(R2);
        } else if (mMedKit.isBandAid()) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mMedKit.isOintment()) {
            Rect R1 = new Rect(tool.getLeft() + (int) (18 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (18 * mDensity), tool.getBottom());
            return R1.intersect(R2);
        } else if (mMedKit.isDefibrillator()) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mMedKit.isPen()) {
            Rect R1 = new Rect(tool.getLeft() + (int) (6 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (16 * mDensity), tool.getBottom());
            return R1.intersect(R2);
        } else {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        }
    }

    public void makeDeviceVibrate(int milliseconds){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else { //deprecated in API 26
            vibrator.vibrate(milliseconds);
        }
    }

    void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option4Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option4Activity.this).inflate(R.layout.dialog_win,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        view.findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option4Activity.this, Option4Activity.class);
                intent.putExtra("difficulty", difficulty);
                finish();
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_next_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option4Activity.this, Option5Activity.class);
                intent.putExtra("difficulty", difficulty);
                finish();
                startActivity(intent);
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    void showPausedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option4Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option4Activity.this).inflate(R.layout.dialog_pause,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final ImageButton btn_back = view.findViewById(R.id.btn_back);
        final ImageButton btn_resume = view.findViewById(R.id.btn_resume);
        final ImageButton btn_restart = view.findViewById(R.id.btn_restart);

        final AlertDialog alertDialog = builder.create();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option4Activity.this, Option4Activity.class);
                intent.putExtra("difficulty", difficulty);
                finish();
                startActivity(intent);
            }
        });

        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHp.resume();
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}
