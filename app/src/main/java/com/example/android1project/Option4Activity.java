package com.example.android1project;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Option4Activity extends AppCompatActivity {
    private float mDensity;

    private ImageView splinter;
    private ImageView used_band_aid;

    private HealthBar mHp;

    private boolean mIsTweezers = false;
    private boolean mIsEpipen = false;
    private boolean mIsBandAid = false;
    private boolean mIsOintment = false;
    private boolean mIsDefibrillator = false;
    private boolean mIsPen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_4_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView white_bg = findViewById(R.id.white_bg_4);
        final ImageView item1 = findViewById(R.id.item4);
        splinter = findViewById(R.id.bee_splinter);
        used_band_aid = findViewById(R.id.girl2_used_band_aid);

        mHp = findViewById(R.id.hp_bar4z);

        final ImageView first_aid_kit = findViewById(R.id.first_aid_kit_4);
        first_aid_kit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Option4Activity.this, v);
                getMenuInflater().inflate(R.menu.first_aid_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.epipen_menu) {
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_epipen);
                            mIsEpipen = true;
                            mIsTweezers = mIsBandAid = mIsOintment = mIsDefibrillator = mIsPen = false;

                        } else if (item.getItemId() == R.id.tweezers_menu) {
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_tweezers_open);
                            mIsTweezers = true;
                            mIsEpipen = mIsBandAid = mIsOintment = mIsDefibrillator = mIsPen = false;

                        } else if (item.getItemId() == R.id.band_aid_menu) {
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_band_aid);
                            mIsBandAid = true;
                            mIsTweezers = mIsEpipen = mIsOintment = mIsDefibrillator = mIsPen = false;

                        } else if (item.getItemId() == R.id.ointment_menu) {
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_ointment);
                            mIsOintment = true;
                            mIsTweezers = mIsEpipen = mIsBandAid = mIsDefibrillator = mIsPen = false;

                        } else if (item.getItemId() == R.id.defibrillator_menu) {
                            //item1.setVisibility(View.VISIBLE);
                            //item1.setImageResource(R.drawable.ic_defibrillator);
                            mIsDefibrillator = true;
                            mIsTweezers = mIsEpipen = mIsBandAid = mIsOintment = mIsPen = false;

                            makeDeviceVibrate(1000);
                            AlphaAnimation anim = new AlphaAnimation(1f, 0f);
                            anim.setDuration(1000);
                            white_bg.startAnimation(anim);
                            mHp.setHp(0);

                        } else if (item.getItemId() == R.id.pen_menu) {
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_pen);
                            mIsPen = true;
                            mIsTweezers = mIsEpipen = mIsBandAid = mIsOintment = mIsDefibrillator = false;

                        } else {
                            item1.setVisibility(View.INVISIBLE);
                        }
                        return false;
                    }
                });
                popupMenu.show();
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
                            if (!isOut && mIsTweezers && checkCollision(item1, splinter) && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                item1.setY(item1.getY() + (10 * mDensity));
                                item1.invalidate();
                                isClosed = true;
                            } else if (!mIsTweezers || !isClosed)
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
                            if (mIsBandAid && checkCollision(item1, findViewById(R.id.girl_2_wound))) {
                                used_band_aid.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.INVISIBLE);
                                Toast.makeText(Option4Activity.this, "Well Done!", Toast.LENGTH_SHORT).show();
                                isBaUsed = true;
                            } else if (!isOut && mIsTweezers && isClosed) {
                                item1.setVisibility(View.INVISIBLE);
                                splinter.setVisibility(View.GONE);
                                isOut = true;
                                isClosed = false;
                            } else if (mIsEpipen && checkCollision(item1, findViewById(R.id.girl_2_thigh))) {
                                item1.setVisibility(View.INVISIBLE);
                                makeDeviceVibrate(250);
                                isEpipenUsed = true;
                            }
                            if (isOut && isBaUsed && isEpipenUsed) {
                                mHp.stop();
                            }
                            if (checkCollision(item1, first_aid_kit)) {
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

        if (mIsEpipen) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop() + (int) (160 * mDensity), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mIsTweezers) {
            Rect R1 = new Rect(tool.getLeft() + (int) (30 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (15 * mDensity), tool.getBottom());
            return R1.intersect(R2);
        } else if (mIsBandAid) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mIsOintment) {
            Rect R1 = new Rect(tool.getLeft() + (int) (18 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (18 * mDensity), tool.getBottom());
            return R1.intersect(R2);
        } else if (mIsDefibrillator) {
            Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
            return R1.intersect(R2);
        } else if (mIsPen) {
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
