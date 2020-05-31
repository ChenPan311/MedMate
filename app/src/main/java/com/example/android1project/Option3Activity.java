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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Option3Activity extends AppCompatActivity {
    private float mDensity;

    private ImageView wound1;
    private ImageView wound2;
    private ImageView wound3;
    private ImageView wound4;
    private ImageView wound5;
    private ImageView wound6;
    private ImageView used_ba1;
    private ImageView used_ba2;
    private ImageView used_ba3;
    private ImageView used_ba4;
    private ImageView used_ba5;
    private ImageView used_ba6;

    private MedKit mMedKit;

    private HealthBar mHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_3_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView white_bg = findViewById(R.id.white_bg_3);
        final ImageView item1 = findViewById(R.id.item3z);

        wound1 = findViewById(R.id.wound_o3_1);
        wound2 = findViewById(R.id.wound_o3_2);
        wound3 = findViewById(R.id.wound_o3_3);
        wound4 = findViewById(R.id.wound_o3_4);
        wound5 = findViewById(R.id.wound_o3_5);
        wound6 = findViewById(R.id.wound_o3_6);
        used_ba1 = findViewById(R.id.used_band_aid_o3_1z);
        used_ba2 = findViewById(R.id.used_band_aid_o3_2z);
        used_ba3 = findViewById(R.id.used_band_aid_o3_3z);
        used_ba4 = findViewById(R.id.used_band_aid_o3_4z);
        used_ba5 = findViewById(R.id.used_band_aid_o3_5z);
        used_ba6 = findViewById(R.id.used_band_aid_o3_6z);

        mHp = findViewById(R.id.hp_bar3z);
        mHp.setActivity(this);

        mMedKit = findViewById(R.id.first_aid_kit_3);
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
            boolean isBa1 = false, isBa2 = false, isBa3 = false, isBa4 = false, isBa5 = false, isBa6 = false;
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
                            v.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (mMedKit.isBandAid() && checkCollision(item1, wound1) && !isBa1) {
                                used_ba1.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa1 = true;
                            } else if (mMedKit.isBandAid() && checkCollision(item1, wound2) && !isBa2) {
                                used_ba2.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa2 = true;
                            } else if (mMedKit.isBandAid() && checkCollision(item1, wound3) && !isBa3) {
                                used_ba3.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa3 = true;
                            } else if (mMedKit.isBandAid() && checkCollision(item1, wound4) && !isBa4) {
                                used_ba4.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa4 = true;
                            } else if (mMedKit.isBandAid() && checkCollision(item1, wound5) && !isBa5) {
                                used_ba5.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa5 = true;
                            } else if (mMedKit.isBandAid() && checkCollision(item1, wound6) && !isBa6) {
                                used_ba6.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                isBa6 = true;
                            }
                            if (isBa1 && isBa2 && isBa3 && isBa4 && isBa5 && isBa6) {
                                mHp.stop();
                            }

                            if (mMedKit.isEpipen() && checkCollision(item1, findViewById(R.id.leg1_o3))) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(mHp.getHp() - 50);
                            } else if (mMedKit.isEpipen() && checkCollision(item1, findViewById(R.id.leg2_o3))) {
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
        Rect R2;
        R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());

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
}
