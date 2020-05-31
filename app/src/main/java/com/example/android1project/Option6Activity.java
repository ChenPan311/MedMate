package com.example.android1project;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Option6Activity extends AppCompatActivity {
    private float mDensity;

    private ImageView blue_face;
    private ImageView mouth;
    private ImageView neck_anatomy;
    private ImageView pen_cricothy;
    private ImageView used_ba;
    private ImageView good_spot;

    private MedKit mMedKit;

    private HealthBar mHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_6);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView white_bg = findViewById(R.id.white_bg_6);
        final ImageView item1 = findViewById(R.id.item6);

        blue_face = findViewById(R.id.girl3_blue_face);
        mouth = findViewById(R.id.girl3_mouth);
        neck_anatomy = findViewById(R.id.neck_anatomy);
        pen_cricothy = findViewById(R.id.pen_cricothy);
        used_ba = findViewById(R.id.used_band_aid_o6_1);
        good_spot = findViewById(R.id.good_spot);

        mHp = findViewById(R.id.hp_bar6);
        mHp.setActivity(this);

        mMedKit = findViewById(R.id.first_aid_kit_6);
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


        item1.setOnTouchListener(new View.OnTouchListener() {
            boolean onNeck = false;

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
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                            layoutParams.topMargin = Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);

                            if (mMedKit.isPen() && checkCollision(item1, neck_anatomy) && neck_anatomy.getVisibility() == View.INVISIBLE) {
                                AlphaAnimation anim = new AlphaAnimation(0f, 0.5f);
                                anim.setDuration(500);
                                anim.setFillAfter(true);
                                neck_anatomy.setVisibility(View.VISIBLE);
                                neck_anatomy.startAnimation(anim);
                            } else if (!checkCollision(item1, neck_anatomy) && neck_anatomy.getVisibility() == View.VISIBLE) {
                                AlphaAnimation anim = new AlphaAnimation(0.5f, 0f);
                                anim.setDuration(500);
                                neck_anatomy.startAnimation(anim);
                                neck_anatomy.setVisibility(View.INVISIBLE);
                            }

                            v.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (mMedKit.isPen() && checkCollision(item1, good_spot) && pen_cricothy.getVisibility() != View.VISIBLE) {
                                makeDeviceVibrate(250);
                                AlphaAnimation anim = new AlphaAnimation(0f, 1f);
                                anim.setDuration(500);
                                pen_cricothy.startAnimation(anim);
                                pen_cricothy.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);

                                AlphaAnimation anim2 = new AlphaAnimation(0.5f, 0f);
                                anim2.setDuration(500);
                                neck_anatomy.startAnimation(anim2);
                                neck_anatomy.setVisibility(View.INVISIBLE);

                                AlphaAnimation anim3 = new AlphaAnimation(1f, 0f);
                                anim3.setDuration(3500);
                                blue_face.startAnimation(anim3);
                                blue_face.setVisibility(View.INVISIBLE);

                                mHp.setHp(mHp.getHp() - 10);
                                //Toast.makeText(Option1Activity.this, "Collision", Toast.LENGTH_SHORT).show();
                            } else if (mMedKit.isPen() && checkCollision(item1, neck_anatomy)) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(0);
                            } else if (mMedKit.isBandAid() && checkCollision(item1, pen_cricothy) && used_ba.getVisibility() != View.VISIBLE) {
                                used_ba.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                mHp.stop();

                                RotateAnimation anim = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                anim.setDuration(1000);
                                anim.setFillAfter(true);
                                mouth.startAnimation(anim);
                            }

                            if (mMedKit.isEpipen() && (checkCollision(item1, findViewById(R.id.body_o6)) || checkCollision(item1, blue_face) || checkCollision(item1, neck_anatomy))) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(mHp.getHp() - 50);
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
        Rect R1, R2;
        if (object.getId() == pen_cricothy.getId()) {
            R2 = new Rect(object.getLeft(), object.getBottom() + (int) (mDensity * 8), object.getLeft() + (int) (mDensity * 24), object.getBottom() + (int) (mDensity * 4));
        } else {
            R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        }

        if (mMedKit.isEpipen()) {
            R1 = new Rect(tool.getLeft(), tool.getTop() + (int) (160 * mDensity), tool.getRight(), tool.getBottom());
        } else if (mMedKit.isTweezers()) {
            R1 = new Rect(tool.getLeft() + (int) (30 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (15 * mDensity), tool.getBottom());
        } else if (mMedKit.isBandAid()) {
            R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        } else if (mMedKit.isOintment()) {
            R1 = new Rect(tool.getLeft() + (int) (18 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (18 * mDensity), tool.getBottom());
        } else if (mMedKit.isDefibrillator()) {
            R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        } else if (mMedKit.isPen()) {
            R1 = new Rect(tool.getLeft() + (int) (6 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (16 * mDensity), tool.getBottom());
        } else {
            R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        }
        return R1.intersect(R2);
    }

    public void makeDeviceVibrate(int milliseconds){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else { //deprecated in API 26
            vibrator.vibrate(milliseconds);
        }
    }
}
