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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

public class Option2Activity extends AppCompatActivity {
    private SharedPreferences mData;

    private int mDifficulty;

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

    private ImageView white_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_2_zoom);


        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mData = getSharedPreferences("score", MODE_PRIVATE);

        mDensity = getResources().getDisplayMetrics().density;

        white_bg = findViewById(R.id.white_bg_2);
        final ImageView item1 = findViewById(R.id.item2);

        final ImageView mouth = findViewById(R.id.girl_1z_mouth);
        final ImageView smile = findViewById(R.id.girl_1z_smile);

        pimples11 = findViewById(R.id.pimples11);
        pimples12 = findViewById(R.id.pimples12);
        pimples13 = findViewById(R.id.pimples13);
        pimples21 = findViewById(R.id.pimples21);
        pimples22 = findViewById(R.id.pimples22);
        pimples23 = findViewById(R.id.pimples23);

        mOintmentWidget = findViewById(R.id.ointment_apply);

        /**<-------getting the user's chosen difficulty and sets the game accordingly------->*/
        mDifficulty = getIntent().getIntExtra("difficulty", 1);
        mHp = findViewById(R.id.hp_bar2z);
        mHp.setActivity(this);
        if (mDifficulty == 1) {
            mHp.setMillis(1000);
        }
        else if (mDifficulty == 2)
            mHp.setMillis(500);
        else if (mDifficulty == 3)
            mHp.setMillis(250);

        mMedKit = findViewById(R.id.first_aid_kit_2);
        mMedKit.setItemId(item1.getId());
        mMedKit.setAllLevelGuide(getIntent().getBooleanExtra("guide", false));
        mMedKit.setOnClickListener(mMedKit);

        /**<-------Setting OnClick Listeners to the MedKit items and buttons------->*/
        ImageView defi = mMedKit.getLayout().findViewById(R.id.defibrillator);
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
                    Toast.makeText(Option2Activity.this, R.string.too_late_toast, Toast.LENGTH_SHORT).show();
            }
        });

        final LottieAnimationView ekg = findViewById(R.id.ekg_2);
        mMedKit.getLayout().findViewById(R.id.ekg_btn).setOnClickListener(new View.OnClickListener() {
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

                ekg.setVisibility(View.VISIBLE);
                ekg.playAnimation();
                ekg.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation){}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ekg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation){}
                    @Override
                    public void onAnimationRepeat(Animator animation){}
                });
            }
        });

        mMedKit.getLayout().findViewById(R.id.help_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMedKit.DismissWindow();
                RelativeLayout mViewGroup = findViewById(R.id.clue_popup);
                LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLayout = mLayoutInflater.inflate(R.layout.clue_popup_window, mViewGroup);
                PopupWindow popupWindow = new PopupWindow(Option2Activity.this);
                popupWindow.setContentView(mLayout);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setAnimationStyle(R.style.MedKitPopupWindowAnimation);
                int OFFSET_X = (int) (20 * mDensity);
                int OFFSET_Y = (int) (70 * mDensity);
                popupWindow.showAtLocation(mLayout, Gravity.NO_GRAVITY, OFFSET_X, (int) (mHp.getY() + OFFSET_Y));
                TextView tv = mLayout.findViewById(R.id.clue_tv);
                tv.setText(R.string.clue_2);
            }
        });

        final RelativeLayout book = findViewById(R.id.open_book);
        mMedKit.getLayout().findViewById(R.id.book).setOnClickListener(new View.OnClickListener() {
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

        /**<-------Pause button's OnClick Listener------->*/
        final ImageButton play_pause_btn = findViewById(R.id.play_pause_btn_2);
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


        /**<-------That's where we decide what happens with each user's decision
         *                   and how will the app react to it------->*/
        item1.setOnTouchListener(new View.OnTouchListener() {
            boolean isApplying = false;

            MakePimplesDisappear mps11 = new MakePimplesDisappear(pimples11, 300);
            MakePimplesDisappear mps12 = new MakePimplesDisappear(pimples12, 300);
            MakePimplesDisappear mps13 = new MakePimplesDisappear(pimples13, 300);
            MakePimplesDisappear mps21 = new MakePimplesDisappear(pimples21, 300);
            MakePimplesDisappear mps22 = new MakePimplesDisappear(pimples22, 300);
            MakePimplesDisappear mps23 = new MakePimplesDisappear(pimples23, 300);

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

                            /**<-------if the user picked ointment and is touching the "pimples"
                             *                  start applying the ointment------->*/
                            if (mMedKit.isOintment() && checkCollision(item1, mOintmentWidget) && !isApplying) {
                                isApplying = true;
                            } else if (!mMedKit.isOintment()) {
                                isApplying = false;
                            }
                            if (isApplying) {
                                mOintmentWidget.applyOintment(item1.getX() - (40 * mDensity), item1.getY() - (20 * mDensity));
                            }

                            /**<-------if the "pimple" are still there, and the ointment is being applied
                             *              make the visible "pimple" disappear gradually------->*/
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

                            /**<-------if the user will try to pop up a "pimple" with a tweezers
                             *                  it will damage the character------->*/
                            if (mMedKit.isTweezers() && (checkCollision(item1, pimples11) || checkCollision(item1, pimples12) ||
                                    checkCollision(item1, pimples13) || checkCollision(item1, pimples21) ||
                                    checkCollision(item1, pimples22) || checkCollision(item1, pimples23))) {
                                mHp.setHp(mHp.getHp() - 1);
                                makeDeviceVibrate(100);
                            }
                            /**<-------Put the tool back in the kit------->*/
                            if (checkCollision(item1, mMedKit)) {
                                item1.setVisibility(View.GONE);
                                layoutParams.leftMargin = (screenWidth - deltaX) / 2;
                                layoutParams.topMargin = (screenHeight - deltaY) / 2;
                            }
                            /**<-------Success!!!------->*/
                            if (pimples11.getAlpha() == 0 && pimples12.getAlpha() == 0 &&
                                    pimples13.getAlpha() == 0 && pimples21.getAlpha() == 0 &&
                                    pimples22.getAlpha() == 0 && pimples23.getAlpha() == 0) {
                                mHp.stop();
                                item1.setVisibility(View.GONE);

                                mData.edit().putInt("user_score_2", mHp.getHp() * mDifficulty).commit();

                                Animation animFadeOut = new AlphaAnimation(1f, 0f);
                                animFadeOut.setDuration(1000);
                                final Animation animFadeIn = new AlphaAnimation(0f, 1f);
                                animFadeIn.setDuration(1000);
                                mouth.startAnimation(animFadeOut);
                                animFadeOut.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        smile.setVisibility(View.VISIBLE);
                                        smile.startAnimation(animFadeIn);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        mouth.setVisibility(View.GONE);
                                        showSuccessDialog();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation){}
                                });
                            }
                            /**<-------if the user will try to use the Epipen it will damage the character severely------->*/
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

    void showDefibrillatorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option2Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option2Activity.this).inflate(R.layout.dialog_defibrillator,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();

        final ImageButton btn_back = view.findViewById(R.id.btn_cancel);
        LottieAnimationView anim = view.findViewById(R.id.count_down_anim);
        anim.setMinAndMaxFrame(300, 600);
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation){}

            @Override
            public void onAnimationEnd(Animator animation) {
                /**<-------if the user used defibrillator it will kill the character------->*/
                alertDialog.dismiss();
                makeDeviceVibrate(1000);
                AlphaAnimation anim = new AlphaAnimation(1f, 0f);
                anim.setDuration(1000);
                white_bg.startAnimation(anim);
                mHp.setHp(0);
            }

            @Override
            public void onAnimationCancel(Animator animation){}
            @Override
            public void onAnimationRepeat(Animator animation){}
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Option2Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option2Activity.this).inflate(R.layout.dialog_win,
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
                Intent intent = new Intent(Option2Activity.this, Option2Activity.class);
                intent.putExtra("difficulty", mDifficulty);
                alertDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_next_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option2Activity.this, Option3Preview.class);
                intent.putExtra("difficulty", mDifficulty);
                intent.putExtra("guide", getIntent().getBooleanExtra("guide", false));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Option2Activity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option2Activity.this).inflate(R.layout.dialog_pause,
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
                Intent intent = new Intent(Option2Activity.this, Option2Activity.class);
                intent.putExtra("difficulty", mDifficulty);
                intent.putExtra("guide", getIntent().getBooleanExtra("guide", false));
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
        /**<-------Calculating and saving here the user's final score------->*/
        int final_score = 0;
        for (int i = 1; i <= 6; i++)
            final_score += mData.getInt("user_score_" + i, 0);

        mData.edit().putInt("final_score", final_score).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        mHp.stop();
        super.onBackPressed();
    }
}