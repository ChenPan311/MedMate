package com.example.android1project;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Option3Preview extends AppCompatActivity {
    private boolean mIsBound = false;
    private MusicService mService;
    private HomeWatcher mHomeWatcher;

    private float mDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_3);


        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**<-------getting the user's chosen difficulty and passing it on------->*/
        final int difficulty = getIntent().getIntExtra("difficulty", 1);

        mDensity = getResources().getDisplayMetrics().density;

        final ImageView victim = findViewById(R.id.victim_3);

        final ImageView magnifier = findViewById(R.id.magnifier3);

        /**<-------Sets the magnifier button's On Click Listener------->*/
        final ImageButton research_btn = findViewById(R.id.research_btn3);
        research_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                research_btn.setAlpha(0.25f);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
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

        /**<-------Moving the magnifier according to the user movement------->*/
        magnifier.setOnTouchListener(new View.OnTouchListener() {
            RelativeLayout.LayoutParams layoutParams;
            int deltaX = 0, deltaY = 0;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

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

                        int screenHeight = displayMetrics.heightPixels;
                        int screenWidth = displayMetrics.widthPixels;

                        layoutParams.leftMargin = Math.min(Math.max(0, (x - deltaX)), screenWidth - v.getWidth());
                        layoutParams.topMargin = Math.min(Math.max(0, (y - deltaY)), screenHeight - v.getHeight() - 100);
                        v.setLayoutParams(layoutParams);
                        break;

                    case MotionEvent.ACTION_UP:
                        /**<-------if the user put the magnifier on the right place move him on
                         *                      to the next screen------->*/
                        if (checkCollision(magnifier, victim)) {
                            Intent intent = new Intent(Option3Preview.this, Option3Activity.class);
                            intent.putExtra("difficulty", difficulty);
                            intent.putExtra("guide", getIntent().getBooleanExtra("guide", false));
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
            public void onRecentAppsPressed() {
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


    public boolean checkCollision(View tool, View object) {
        Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getLeft() + (int) (90 * mDensity), tool.getTop() + (int) (90 * mDensity));
        Rect R2 = new Rect(object.getLeft(), object.getBottom() - (int) (120 * mDensity), object.getRight(), object.getBottom());
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
        /**<----- Background Music: Unbind music ----->**/
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);
        mHomeWatcher.stopWatch();
    }
}
