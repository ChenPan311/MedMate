package com.example.android1project;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Option1Preview extends AppCompatActivity {
    private boolean mIsBound = false;
    private MusicService mService;
    private HomeWatcher mHomeWatcher;

    private float mDensity;

    private ImageView btn_guide;
    private boolean guide = false;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_1);


        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**<-------getting the user's chosen difficulty and passing it on------->*/
        final int difficulty = getIntent().getIntExtra("difficulty", 1);

        btn_guide = findViewById(R.id.research_btn_guide);
        anim = AnimationUtils.loadAnimation(Option1Preview.this, R.anim.fade_in_out);
        btn_guide.setAnimation(anim);

        /**<-------if the user chose 'New Game' and it's the first time he's opening this level
         *              ask the user if he'd like guidance through this level------->*/
        if (getIntent().getBooleanExtra("guidance", false))
            showGuidanceDialog();

        mDensity = getResources().getDisplayMetrics().density;

        final ImageView victim = findViewById(R.id.victim_1);

        final ImageView magnifier = findViewById(R.id.magnifier1);

        /**<-------Sets the magnifier button's On Click Listener------->*/
        final ImageButton research_btn = findViewById(R.id.research_btn1);
        research_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                research_btn.setAlpha(0.25f);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(50);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                scaleAnimation.setRepeatCount(1);
                research_btn.startAnimation(scaleAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        research_btn.setAlpha(1f);
                    }
                }, 50);
                magnifier.setVisibility(View.VISIBLE);
            }
        });
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                research_btn.performClick();

                btn_guide.setVisibility(View.GONE);
                btn_guide.clearAnimation();

                ScaleAnimation scaleAnimation1 = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                scaleAnimation1.setDuration(1000);
                findViewById(R.id.magnifier_guide_1).setAnimation(scaleAnimation1);
                findViewById(R.id.magnifier_guide_1).setVisibility(View.VISIBLE);

                findViewById(R.id.magnifier_guide_2).setAnimation(anim);
                findViewById(R.id.magnifier_guide_2).setVisibility(View.VISIBLE);
                findViewById(R.id.magnifier_guide_2).startAnimation(anim);
            }
        });


        /**<-------Moving the magnifier according to the user movement------->*/
        magnifier.setOnTouchListener(new View.OnTouchListener() {
            RelativeLayout.LayoutParams layoutParams;
            int deltaX = 0, deltaY = 0;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            final int screenHeight = displayMetrics.heightPixels;
            final int screenWidth = displayMetrics.widthPixels;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                        /**<-------if the user put the magnifier on the right place move him on
                         *                      to the next screen------->*/
                        if (checkCollision(magnifier, victim)) {
                            Intent intent = new Intent(Option1Preview.this, Option1Activity.class);
                            intent.putExtra("difficulty", difficulty);
                            if (guide)
                                intent.putExtra("guide", true);
                            finish();
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_in_to, R.anim.zoom_in_from);
                        }
                        break;
                }
                v.requestLayout();
                return true;
            }
        });


        /**<----- Background Music Set Up ----->**/
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mService != null) {
                    mService.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mService != null) {
                    mService.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

        doBindService();
        final Intent music = new Intent();
        music.setClass(this, MusicService.class);
    }


    /**<----- Background Music Methods ----->**/
    private ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mService = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this,MusicService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound) {
            unbindService(serviceConnection);
            mIsBound = false;
        }
    }
    /**<----- Background Music Methods ----->**/


    void showGuidanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Option1Preview.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Option1Preview.this).inflate(R.layout.dialog_guidance,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final ImageButton btn_cancel = view.findViewById(R.id.btn_cancel);
        final ImageButton btn_confirm = view.findViewById(R.id.btn_confirm);

        final AlertDialog alertDialog = builder.create();

        /**<-------getting the user's decision whether to take guidance or not
         *                  and sets the level accordingly------->*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide = false;
                alertDialog.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                guide = true;
                anim = AnimationUtils.loadAnimation(Option1Preview.this, R.anim.fade_in_out);
                btn_guide.setVisibility(View.VISIBLE);
                btn_guide.startAnimation(anim);
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public boolean checkCollision(View tool, View object) {
        Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getLeft() + (int) (90 * mDensity), tool.getTop() + (int) (90 * mDensity));
        Rect R2 = new Rect(object.getLeft(), object.getBottom() - (int) (190 * mDensity), object.getRight(), object.getBottom());
        return R1.intersect(R2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**<----- Background Music: If the user didn't mute the music resume background music ----->**/
        if (mService != null && mService.isSoundOn())
            mService.resumeMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**<----- Background Music: Detect idle screen to stop music ----->**/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mService != null) {
                mService.pauseMusic();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**<----- Background Music: Unbind background music ----->**/
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);
        mHomeWatcher.stopWatch();
    }
}
