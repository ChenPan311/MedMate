package com.example.android1project;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Option1Activity extends AppCompatActivity {
    private SharedPreferences mData;

    private int mDifficulty;

    private float mDensity;

    private MedKit mMedKit;

    private HealthBar mHp;

    private ImageView white_bg;

    /*private boolean mIsTweezers = false;
    private boolean mIsEpipen = false;
    private boolean mIsBandAid = false;
    private boolean mIsOintment = false;
    private boolean mIsDefibrillator = false;
    private boolean mIsPen = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_1_zoom);

        mData = getSharedPreferences("score", MODE_PRIVATE);

        mDensity = getResources().getDisplayMetrics().density;

        mDifficulty = getIntent().getIntExtra("difficulty", 1);
        mHp = findViewById(R.id.hp_bar1z);
        mHp.setActivity(Option1Activity.this);
        if (mDifficulty == 1) {
            mHp.setMillis(1000);
        }
        else if (mDifficulty == 2)
            mHp.setMillis(500);
        else if (mDifficulty == 3)
            mHp.setMillis(250);

        white_bg = findViewById(R.id.white_bg_1);
        final ImageView thorn1, thorn2, thorn3, thorn4, thorn5, thorn6;

        final ImageView item1 = findViewById(R.id.item1);

        thorn1 = findViewById(R.id.thorn_1z);
        thorn2 = findViewById(R.id.thorn_2z);
        thorn3 = findViewById(R.id.thorn_3z);
        thorn4 = findViewById(R.id.thorn_4z);
        thorn5 = findViewById(R.id.thorn_5z);
        thorn6 = findViewById(R.id.thorn_6z);

        mMedKit = findViewById(R.id.first_aid_kit_1);
        mMedKit.setItemId(item1.getId());
        mMedKit.setOnClickListener(mMedKit);
        /*final ImageView first_aid_kit = findViewById(R.id.first_aid_kit_1);
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
        });*/

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

                if ((mDifficulty == 1 && mHp.getHp() > 5) || (mDifficulty == 2 && mHp.getHp() > 10) || (mDifficulty == 3 && mHp.getHp() > 20))
                    showDefibrillatorDialog();
                else
                    Toast.makeText(Option1Activity.this, R.string.too_late_toast, Toast.LENGTH_SHORT).show();
            }
        });

        final LottieAnimationView ekg = findViewById(R.id.ekg_1);
        mMedKit.mLayout.findViewById(R.id.ekg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekg.setVisibility(View.VISIBLE);
                ekg.playAnimation();
                ekg.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ekg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                mMedKit.DismissWindow();
            }
        });

        final ImageButton play_pause_btn = findViewById(R.id.play_pause_btn_1);
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
                            //item1.getTag().equals("tweezers")
                            if (mMedKit.isTweezers() && checkCollision(item1, thorn1) && !isOut1 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn1.getX() + thorn2.getWidth() + (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn1 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mMedKit.isTweezers() && checkCollision(item1, thorn2) && !isOut2 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn2.getX() + thorn2.getWidth() + (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn2 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mMedKit.isTweezers() && checkCollision(item1, thorn3) && !isOut3 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn3.getX() + thorn2.getWidth() - (10 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn3 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mMedKit.isTweezers() && checkCollision(item1, thorn4) && !isOut4 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn4.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn4 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mMedKit.isTweezers() && checkCollision(item1, thorn5) && !isOut5 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn5.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn5 = isClosed = true;
                                isFirstThorn = false;
                            } else if (mMedKit.isTweezers() && checkCollision(item1, thorn6) && !isOut6 && !isClosed) {
                                item1.setImageResource(R.drawable.ic_tweezers_close);
                                //item1.setX(item1.getX() + (20 * mDensity));
                                //item1.setX(thorn6.getX() + (3 * mDensity));
                                item1.setY(isFirstThorn ? item1.getY() + (3 * mDensity) : item1.getY());
                                item1.invalidate();
                                isThorn6 = isClosed = true;
                                isFirstThorn = false;
                            } else if (!mMedKit.isTweezers() || !isClosed)
                                isClosed = false;

                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                            layoutParams.topMargin = isClosed ? layoutParams.topMargin : Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);
                            v.setLayoutParams(layoutParams);

                            float thorn1X, thorn2X, thorn3X, thorn4X, thorn5X, thorn6X;
                            if (isClosed && isThorn1) {
                                thorn1X = thorn1.getX();

                                thorn1.setX(Math.max(item1.getX() - thorn1.getWidth() + (30 * mDensity), findViewById(R.id.skin_1z).getX()));
                                thorn1.setLeft((int) (Math.max(item1.getX() - thorn1.getWidth() + (30 * mDensity), findViewById(R.id.skin_1z).getX())));
                                //thorn1.setX(item1.getX() - thorn1.getWidth() + (30 * mDensity));
                                //thorn1.setY(item1.getY() + item1.getHeight() - (10 * mDensity));
                                thorn1.invalidate();

                                if (thorn1X > thorn1.getX() && checkSimpleCollision(thorn1, findViewById(R.id.skin_1z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            } else if (isClosed && isThorn2) {
                                thorn2X = thorn2.getX();

                                thorn2.setX(Math.max(item1.getX() - thorn2.getWidth() + (30 * mDensity), findViewById(R.id.skin_2z).getX()));
                                thorn2.setLeft((int) (Math.max(item1.getX() - thorn2.getWidth() + (30 * mDensity), findViewById(R.id.skin_2z).getX())));
                                thorn2.invalidate();

                                if (thorn2X > thorn2.getX() && checkSimpleCollision(thorn2, findViewById(R.id.skin_2z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            } else if (isClosed && isThorn3) {
                                thorn3X = thorn3.getX();

                                thorn3.setX(Math.max(item1.getX() - thorn3.getWidth() + (30 * mDensity), findViewById(R.id.skin_3z).getX()));
                                thorn3.setLeft((int) (Math.max(item1.getX() - thorn3.getWidth() + (30 * mDensity), findViewById(R.id.skin_3z).getX())));
                                thorn3.invalidate();

                                if (thorn3X > thorn3.getX() && checkSimpleCollision(thorn3, findViewById(R.id.skin_3z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            } else if (isClosed && isThorn4) {
                                thorn4X = thorn4.getX();

                                thorn4.setX(Math.min(item1.getX() + (15 * mDensity), findViewById(R.id.skin_4z).getX() - (17 * mDensity)));
                                thorn4.setRight((int) (item1.getX() + thorn4.getWidth() + (15 * mDensity)));
                                //thorn4.setX(item1.getX() + (15 * mDensity));
                                thorn4.invalidate();

                                if (thorn4X < thorn4.getX() && checkSimpleCollision(thorn4, findViewById(R.id.skin_4z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            } else if (isClosed && isThorn5) {
                                thorn5X = thorn5.getX();

                                thorn5.setX(Math.min(item1.getX() + (15 * mDensity), findViewById(R.id.skin_5z).getX() - (17 * mDensity)));
                                thorn5.setRight((int) (item1.getX() + thorn5.getWidth() + (15 * mDensity)));
                                //thorn5.setX(item1.getX() + (15 * mDensity));
                                thorn5.invalidate();

                                if (thorn5X < thorn5.getX() && checkSimpleCollision(thorn5, findViewById(R.id.skin_5z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            } else if (isClosed && isThorn6) {
                                thorn6X = thorn6.getX();

                                thorn6.setX(Math.min(item1.getX() + (15 * mDensity), findViewById(R.id.skin_6z).getX() - (17 * mDensity)));
                                thorn6.setRight((int) (item1.getX() + thorn6.getWidth() + (15 * mDensity)));
                                //thorn6.setX(item1.getX() + (15 * mDensity));
                                thorn6.invalidate();

                                if (thorn6X < thorn6.getX() && checkSimpleCollision(thorn6, findViewById(R.id.skin_6z)))
                                    mHp.setHp(mHp.getHp() - 1);
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (!isOut1 && isThorn1 && !checkSimpleCollision(thorn1, findViewById(R.id.skin_1z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                //item1.setX(item1.getX() - (20 * mDensity));
                                item1.invalidate();
                                thorn1.setVisibility(View.GONE);
                                isClosed = isThorn1 = false;
                                isOut1 = true;
                            } else if (!isOut2 && isThorn2 && !checkSimpleCollision(thorn2, findViewById(R.id.skin_2z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                item1.invalidate();
                                thorn2.setVisibility(View.GONE);
                                isClosed = isThorn2 = false;
                                isOut2 = true;
                            } else if (!isOut3 && isThorn3 && !checkSimpleCollision(thorn3, findViewById(R.id.skin_3z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                item1.invalidate();
                                thorn3.setVisibility(View.GONE);
                                isClosed = isThorn3 = false;
                                isOut3 = true;
                            } else if (!isOut4 && isThorn4 && !checkSimpleCollision(thorn4, findViewById(R.id.skin_4z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                item1.invalidate();
                                thorn4.setVisibility(View.GONE);
                                isClosed = isThorn4 = false;
                                isOut4 = true;
                            } else if (!isOut5 && isThorn5 && !checkSimpleCollision(thorn5, findViewById(R.id.skin_5z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                item1.invalidate();
                                thorn5.setVisibility(View.GONE);
                                isClosed = isThorn5 = false;
                                isOut5 = true;
                            } else if (!isOut6 && isThorn6 && !checkSimpleCollision(thorn6, findViewById(R.id.skin_6z))) {
                                item1.setImageResource(R.drawable.ic_tweezers_open);
                                item1.invalidate();
                                thorn6.setVisibility(View.GONE);
                                isClosed = isThorn6 = false;
                                isOut6= true;
                            }

                            if (isOut1 && isOut2 && isOut3 && isOut4 && isOut5 && isOut6) {
                                mHp.stop();

                                mData.edit().putInt("user_score_1", mHp.getHp() * mDifficulty).commit();

                                showSuccessDialog();
                            }

                            if (mMedKit.isEpipen() && checkCollision(item1, findViewById(R.id.leg1_o1))) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(mHp.getHp() - 50);
                            } else if (mMedKit.isEpipen() && checkCollision(item1, findViewById(R.id.leg2_o1))) {
                                makeDeviceVibrate(1000);
                                mHp.setHp(mHp.getHp() - 50);
                            }

                            if (checkCollision(item1, mMedKit/*first_aid_kit*/)) {
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

    public boolean checkSimpleCollision(View tool, View object) {
        Rect R1, R2;
        R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
        return R1.intersect(R2);
    }

    public void makeDeviceVibrate(int milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else { //deprecated in API 26
            vibrator.vibrate(milliseconds);
        }
    }

    void showDefibrillatorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option1Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option1Activity.this).inflate(R.layout.dialog_defibrillator,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        final ImageButton btn_back = view.findViewById(R.id.btn_cancel);
        LottieAnimationView anim = view.findViewById(R.id.count_down_anim);
        /*anim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );*/
        anim.setMinAndMaxFrame(300, 600);
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                alertDialog.dismiss();
                makeDeviceVibrate(1000);
                AlphaAnimation anim = new AlphaAnimation(1f, 0f);
                anim.setDuration(1000);
                white_bg.startAnimation(anim);
                mHp.setHp(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option1Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option1Activity.this).inflate(R.layout.dialog_win,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });

        view.findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option1Activity.this, Option1Activity.class);
                intent.putExtra("difficulty", mDifficulty);
                alertDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_next_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option1Activity.this, Option2Preview.class);
                intent.putExtra("difficulty", mDifficulty);
                alertDialog.dismiss();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Option1Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option1Activity.this).inflate(R.layout.dialog_pause,
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
                alertDialog.dismiss();
                finish();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option1Activity.this, Option1Activity.class);
                intent.putExtra("difficulty", mDifficulty);
                alertDialog.dismiss();
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

    @Override
    protected void onPause() {
        super.onPause();

        int final_score = 0;
        for (int i = 1; i <= 6; i++)
            final_score += mData.getInt("user_score_" + i, 0);

        mData.edit().putInt("final_score", final_score).commit();
    }

    @Override
    public void onBackPressed() {
        mHp.stop();
        super.onBackPressed();
    }
}
