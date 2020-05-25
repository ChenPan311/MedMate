package com.example.android1project;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Option2Activity extends AppCompatActivity {
    private float mDensity;

    private ImageView pimples11;
    private ImageView pimples12;
    private ImageView pimples13;
    private ImageView pimples21;
    private ImageView pimples22;
    private ImageView pimples23;

    private OintmentWidget ointment_apply;

    private boolean mIsTweezers = false;
    private boolean mIsEpipen = false;
    private boolean mIsBandAid = false;
    private boolean mIsOintment = false;
    private boolean mIsDefibrillator = false;
    private boolean mIsPen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_2_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView item1 = findViewById(R.id.item2);

        pimples11 = findViewById(R.id.pimples11);
        pimples12 = findViewById(R.id.pimples12);
        pimples13 = findViewById(R.id.pimples13);
        pimples21 = findViewById(R.id.pimples21);
        pimples22 = findViewById(R.id.pimples22);
        pimples23 = findViewById(R.id.pimples23);

        ointment_apply = findViewById(R.id.ointment_apply);

        final ImageView first_aid_kit = findViewById(R.id.first_aid_kit_2);
        first_aid_kit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Option2Activity.this, v);
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
                            item1.setVisibility(View.VISIBLE);
                            item1.setImageResource(R.drawable.ic_defibrillator);
                            mIsDefibrillator = true;
                            mIsTweezers = mIsEpipen = mIsBandAid = mIsOintment = mIsPen = false;

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


                item1.setOnTouchListener(new View.OnTouchListener() {
                    boolean isApplying = false;
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
                                    Log.d("ScreenHeight", screenHeight + " " + (layoutParams.topMargin + item1.getHeight() + 96));
                                    Log.d("screenWidth", screenWidth + " " + (layoutParams.leftMargin + item1.getWidth()));

                                    layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                                    layoutParams.topMargin = Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);

                                    if (mIsOintment && checkCollision(item1, ointment_apply) && !isApplying) {
                                        ointment_apply.startApplyOintment(item1.getX() - (item1.getWidth() / 2f), item1.getY() - (5f * mDensity));
                                        isApplying = true;
                                    } else if (!mIsOintment || !isApplying) {
                                        isApplying = false;
                                    }

                                    if (isApplying) {
                                        ointment_apply.applyOintment(item1.getX() - (item1.getWidth() / 1.5f), item1.getY() - (item1.getHeight() / 8f));
                                    }

                                    v.setLayoutParams(layoutParams);
                                    break;

                                case MotionEvent.ACTION_UP:
                                    layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                                    if (checkCollision(item1, pimples11)) {
                                        makeDeviceVibrate(250);
                                        //Toast.makeText(Option1Activity.this, "Collision", Toast.LENGTH_SHORT).show();
                                    }
                                    if (checkCollision(item1, first_aid_kit)) {
                                        item1.setVisibility(View.GONE);
                                        layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                        layoutParams.topMargin = (screenHeight - deltaY) / 2;
                                    }
                                    isApplying = false;
                                    break;
                            }
                            v.requestLayout();
                        }
                        return true;
                    }
                });
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
