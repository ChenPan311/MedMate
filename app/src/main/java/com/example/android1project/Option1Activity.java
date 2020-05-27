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

import androidx.appcompat.app.AppCompatActivity;

public class Option1Activity extends AppCompatActivity {
    private float mDensity;

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
        setContentView(R.layout.activity_option_1_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        mHp = findViewById(R.id.hp_bar1z);

        final ImageView white_bg = findViewById(R.id.white_bg_1);
        final ImageView thorn1, thorn2, thorn3, thorn4, thorn5, thorn6;

        final ImageView item1 = findViewById(R.id.item1);

        thorn1 = findViewById(R.id.thorn_1z);
        thorn2 = findViewById(R.id.thorn_2z);
        thorn3 = findViewById(R.id.thorn_3z);
        thorn4 = findViewById(R.id.thorn_4z);
        thorn5 = findViewById(R.id.thorn_5z);
        thorn6 = findViewById(R.id.thorn_6z);

        final ImageView first_aid_kit = findViewById(R.id.first_aid_kit_1);
        first_aid_kit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Option1Activity.this, v);
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
            boolean isThorn1 = false, isThorn2 = false, isThorn3 = false,
                    isThorn4 = false, isThorn5 = false, isThorn6 = false,
                    isOut1 = false, isOut2 = false, isOut3 = false, isOut4 = false,
                    isOut5 = false, isOut6 = false;
            boolean isFirstThorn = true;
            boolean isClosed = false;
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
                            if (mIsTweezers && checkCollision(item1, thorn1) && !isOut1 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn1.getX() + thorn2.getWidth() + (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn1 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mIsTweezers && checkCollision(item1, thorn2) && !isOut2 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn2.getX() + thorn2.getWidth() + (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn2 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mIsTweezers && checkCollision(item1, thorn3) && !isOut3 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn3.getX() + thorn2.getWidth() - (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn3 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mIsTweezers && checkCollision(item1, thorn4) && !isOut4 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn4.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn4 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mIsTweezers && checkCollision(item1, thorn5) && !isOut5 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn5.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn5 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mIsTweezers && checkCollision(item1, thorn6) && !isOut6 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn6.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn6 = isClosed = true;
                                isFirstThorn = false;
                            } else if (!mIsTweezers || !isClosed)
                                isClosed = false;

                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                            layoutParams.topMargin = isClosed ? layoutParams.topMargin : Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);
                            v.setLayoutParams(layoutParams);

                            if (isClosed && isThorn1) {
                                thorn1.setX(item1.getX() - thorn1.getWidth() + (30 * mDensity));
                                //thorn1.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn1.invalidate();
                            } else if (isClosed && isThorn2) {
                                thorn2.setX(item1.getX() - thorn2.getWidth() + (30 * mDensity));
                                //thorn2.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn2.invalidate();
                            } else if (isClosed && isThorn3) {
                                thorn3.setX(item1.getX() - thorn3.getWidth() + (30 * mDensity));
                                //thorn3.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn3.invalidate();
                            } else if (isClosed && isThorn4) {
                                thorn4.setX(item1.getX() + (15 * mDensity));
                                //thorn4.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn4.invalidate();
                            } else if (isClosed && isThorn5) {
                                thorn5.setX(item1.getX() + (15 * mDensity));
                                //thorn5.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn5.invalidate();
                            } else if (isClosed && isThorn6) {
                                thorn6.setX(item1.getX() + (15 * mDensity));
                                //thorn6.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn6.invalidate();
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (!isOut1 && isThorn1) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn1.setVisibility(View.GONE);
                                isClosed = isThorn1 = false;
                                isOut1 = true;
                            } else if (!isOut2 && isThorn2) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn2.setVisibility(View.GONE);
                                isClosed = isThorn2 = false;
                                isOut2 = true;
                            } else if (!isOut3 && isThorn3) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn3.setVisibility(View.GONE);
                                isClosed = isThorn3 = false;
                                isOut3 = true;
                            } else if (!isOut4 && isThorn4) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn4.setVisibility(View.GONE);
                                isClosed = isThorn4 = false;
                                isOut4 = true;
                            } else if (!isOut5 && isThorn5) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn5.setVisibility(View.GONE);
                                isClosed = isThorn5 = false;
                                isOut5 = true;
                            } else if (!isOut6 && isThorn6) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn6.setVisibility(View.GONE);
                                isClosed = isThorn6 = false;
                                isOut6= true;
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
        Rect R1, R2;
        R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());

        if (mIsEpipen) {
            R1 = new Rect(tool.getLeft(), tool.getTop() + (int) (160 * mDensity), tool.getRight(), tool.getBottom());
        } else if (mIsTweezers) {
            R1 = new Rect(tool.getLeft() + (int) (30 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (15 * mDensity), tool.getBottom());
        } else if (mIsBandAid) {
            R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        } else if (mIsOintment) {
            R1 = new Rect(tool.getLeft() + (int) (18 * mDensity), tool.getBottom() - (int) (20 * mDensity), tool.getRight() - (int) (18 * mDensity), tool.getBottom());
        } else if (mIsDefibrillator) {
            R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        } else if (mIsPen) {
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
