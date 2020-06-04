package com.example.android1project;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Option4Activity extends AppCompatActivity {
    private SharedPreferences mData;

    private int mDifficulty;

    private float mDensity;

    private ImageView splinter;
    private ImageView used_band_aid;

    private MedKit mMedKit;

    private HealthBar mHp;

    private ImageView white_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_4_zoom);

        mData = getSharedPreferences("score", MODE_PRIVATE);

        mDensity = getResources().getDisplayMetrics().density;

        white_bg = findViewById(R.id.white_bg_4);
        final ImageView item1 = findViewById(R.id.item4);
        splinter = findViewById(R.id.bee_splinter);
        used_band_aid = findViewById(R.id.girl2_used_band_aid);


        mDifficulty = getIntent().getIntExtra("difficulty", 1);
        mHp = findViewById(R.id.hp_bar4z);
        mHp.setActivity(this);
        if (mDifficulty == 1) {
            mHp.setMillis(1000);
        }
        else if (mDifficulty == 2)
            mHp.setMillis(500);
        else if (mDifficulty == 3)
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

                if ((mDifficulty == 1 && mHp.getHp() > 5) || (mDifficulty == 2 && mHp.getHp() > 10) || (mDifficulty == 3 && mHp.getHp() > 20))
                    showDefibrillatorDialog();
                else
                    Toast.makeText(Option4Activity.this, R.string.too_late_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mMedKit.mLayout.findViewById(R.id.help_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMedKit.DismissWindow();
                RelativeLayout mViewGroup = findViewById(R.id.clue_popup);
                LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLayout = mLayoutInflater.inflate(R.layout.clue_popup_window, mViewGroup);
                PopupWindow popupWindow = new PopupWindow(Option4Activity.this);
                popupWindow.setContentView(mLayout);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setAnimationStyle(R.style.MedKitPopupWindowAnimation);
                int OFFSET_X = (int) (20 * mDensity);
                int OFFSET_Y = (int) (70 * mDensity);
                popupWindow.showAtLocation(mLayout, Gravity.NO_GRAVITY, OFFSET_X, (int) (mHp.getY() + OFFSET_Y));
                TextView tv = mLayout.findViewById(R.id.clue_tv);
                tv.setText(R.string.clue_4);
            }
        });

        final RelativeLayout book = findViewById(R.id.open_book);
        mMedKit.mLayout.findViewById(R.id.book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1.setVisibility(View.GONE);
                mMedKit.setIsTweezers(false);
                mMedKit.setIsBandAid(false);
                mMedKit.setIsOintment(false);
                mMedKit.setIsEpipen(false);
                mMedKit.setIsDefibrillator(false);
                mMedKit.setIsPen(false);
                mMedKit.DismissWindow();

                ScaleAnimation anim = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(200);
                book.startAnimation(anim);
                book.setVisibility(View.VISIBLE);
                findViewById(R.id.close_book_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScaleAnimation anim2 = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        anim2.setDuration(200);
                        book.startAnimation(anim2);
                        book.setVisibility(View.GONE);
                    }
                });
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
                            } else if (!mMedKit.isTweezers() || !isClosed) {
                                isClosed = false;
                            }

                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                            layoutParams.topMargin = isClosed ? layoutParams.topMargin : Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);
                            v.setLayoutParams(layoutParams);

                            float splinterX;
                            if (!isOut && isClosed) {
                                splinterX = splinter.getX();

                                splinter.setX(Math.max(item1.getX() - splinter.getWidth() + (40 * mDensity), findViewById(R.id.girl_2_wound).getX()));
                                splinter.setLeft((int) (Math.max(item1.getX() - splinter.getWidth() + (40 * mDensity), findViewById(R.id.girl_2_wound).getX())));
                                //splinter.setY(item1.getY() + item1.getHeight() - (15 * mDensity));
                                splinter.invalidate();

                                if (splinterX > splinter.getX() && checkSimpleCollision(splinter, findViewById(R.id.girl_2_wound)))
                                    mHp.setHp(mHp.getHp() - 1);
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            if (!isBaUsed && isOut && mMedKit.isBandAid() && checkCollision(item1, findViewById(R.id.girl_2_wound))) {
                                used_band_aid.setVisibility(View.VISIBLE);
                                item1.setVisibility(View.INVISIBLE);
                                isBaUsed = true;
                            } else if (!isOut && mMedKit.isTweezers() && isClosed && !checkSimpleCollision(splinter, findViewById(R.id.girl_2_wound))) {
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

                                mData.edit().putInt("user_score_4", mHp.getHp() * mDifficulty).commit();

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

    public boolean checkSimpleCollision(View tool, View object) {
        Rect R1, R2;
        R2 = new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getRight(), tool.getBottom());
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

    void showDefibrillatorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option4Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option4Activity.this).inflate(R.layout.dialog_defibrillator,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        final ImageButton btn_back = view.findViewById(R.id.btn_cancel);
        LottieAnimationView anim = view.findViewById(R.id.count_down_anim);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Option4Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option4Activity.this).inflate(R.layout.dialog_win,
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
                Intent intent = new Intent(Option4Activity.this, Option4Activity.class);
                intent.putExtra("difficulty", mDifficulty);
                alertDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_next_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option4Activity.this, Option5Activity.class);
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
                alertDialog.dismiss();
                finish();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option4Activity.this, Option4Activity.class);
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
