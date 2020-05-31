package com.example.android1project;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Option2Activity extends AppCompatActivity {
    private float mDensity;

    private ImageView pimples11;
    private ImageView pimples12;
    private ImageView pimples13;
    private ImageView pimples21;
    private ImageView pimples22;
    private ImageView pimples23;

    private OintmentWidget mOintmentWidget;

    private MedKit mMedKit;

    private HealthBar mHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_2_zoom);
        mDensity = getResources().getDisplayMetrics().density;

        final ImageView white_bg = findViewById(R.id.white_bg_2);
        final ImageView item1 = findViewById(R.id.item2);

        pimples11 = findViewById(R.id.pimples11);
        pimples12 = findViewById(R.id.pimples12);
        pimples13 = findViewById(R.id.pimples13);
        pimples21 = findViewById(R.id.pimples21);
        pimples22 = findViewById(R.id.pimples22);
        pimples23 = findViewById(R.id.pimples23);

        mOintmentWidget = findViewById(R.id.ointment_apply);

        mHp = findViewById(R.id.hp_bar2z);
        mHp.setActivity(this);

        mMedKit = findViewById(R.id.first_aid_kit_2);
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
            boolean isApplying = false;
                    /*boolean isDisappearing11 = false, isDisappearing12 = false, isDisappearing13 = false,
                    isDisappearing21 = false, isDisappearing22 = false, isDisappearing23 = false;*/

            MakePimplesDisappear mps11 = new MakePimplesDisappear(pimples11, 500);
            MakePimplesDisappear mps12 = new MakePimplesDisappear(pimples12, 500);
            MakePimplesDisappear mps13 = new MakePimplesDisappear(pimples13, 500);
            MakePimplesDisappear mps21 = new MakePimplesDisappear(pimples21, 500);
            MakePimplesDisappear mps22 = new MakePimplesDisappear(pimples22, 500);
            MakePimplesDisappear mps23 = new MakePimplesDisappear(pimples23, 500);

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

                            if (mMedKit.isOintment() && checkCollision(item1, mOintmentWidget) && !isApplying) {
                                isApplying = true;
                            } else if (!mMedKit.isOintment()) {
                                isApplying = false;
                            }
                            if (isApplying) {
                                mOintmentWidget.applyOintment(item1.getX() - (40 * mDensity), item1.getY() - (20 * mDensity));
                            }

                            if (mMedKit.isOintment() && checkCollision(item1, pimples11) && !mps11.isActive()) {
                                mps11.makePimpleDisappear();
                            }
                            if (mMedKit.isOintment() && checkCollision(item1, pimples12) && !mps12.isActive()) {
                                mps12.makePimpleDisappear();
                            }
                            if (mMedKit.isOintment() && checkCollision(item1, pimples13) && !mps13.isActive()) {
                                mps13.makePimpleDisappear();
                            }
                            if (mMedKit.isOintment() && checkCollision(item1, pimples21) && !mps21.isActive()) {
                                mps21.makePimpleDisappear();
                            }
                            if (mMedKit.isOintment() && checkCollision(item1, pimples22) && !mps22.isActive()) {
                                mps22.makePimpleDisappear();
                            }
                            if (mMedKit.isOintment() && checkCollision(item1, pimples23) && !mps23.isActive()) {
                                mps23.makePimpleDisappear();
                            }

                            v.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (mMedKit.isTweezers() && (checkCollision(item1, pimples11) || checkCollision(item1, pimples12) ||
                                    checkCollision(item1, pimples13) || checkCollision(item1, pimples21) ||
                                    checkCollision(item1, pimples22) || checkCollision(item1, pimples23))) {
                                mHp.setHp(mHp.getHp() - 1);
                                makeDeviceVibrate(100);
                            }
                            if (checkCollision(item1, mMedKit)) { /*Put the tool back in the kit*/
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                            }
                            if (pimples11.getAlpha() == 0 && pimples12.getAlpha() == 0 &&
                                    pimples13.getAlpha() == 0 && pimples21.getAlpha() == 0 &&
                                    pimples22.getAlpha() == 0 && pimples23.getAlpha() == 0) { /*Success*/
                                mHp.stop();
                                Toast.makeText(Option2Activity.this, "Well done!", Toast.LENGTH_SHORT).show();
                            }

                            if (mMedKit.isEpipen() && (checkCollision(item1, findViewById(R.id.body_o2)) || checkCollision(item1, mOintmentWidget))) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(mHp.getHp() - 50);
                            }

                            isApplying = false;
                            mOintmentWidget.finishApplying();
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