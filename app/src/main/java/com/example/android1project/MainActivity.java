package com.example.android1project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean mSoundOn = true;
    private boolean mIsBound = false;
    private MusicService mService;
    HomeWatcher mHomeWatcher;

    private SharedPreferences mData;

    private Button btn_resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**<-------Hides the status bar------->**/
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final ImageView background_1 = findViewById(R.id.background_1);
        final ImageView background_2 = findViewById(R.id.background_2);

        /**<-------Makes the background move------->**/
        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(20000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = background_1.getWidth();
                final float translationX = width * progress;
                background_1.setTranslationX(translationX);
                background_2.setTranslationX(translationX - width);
            }
        });
        animator.start();

        btn_resume = findViewById(R.id.btn_resume_game);

        /**<-------if the user started a game already show him the 'Resume Game' option------->**/
        mData = getSharedPreferences("score", MODE_PRIVATE);
        if (mData.getInt("user_score_1", 0) > 0) {
            btn_resume.setVisibility(View.VISIBLE);
        }

        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", mData.getInt("difficulty", 1));
                startActivity(intent);
            }
        });

        /**<-------if the user chooses 'New Game' ask him which difficulty we'd like------->**/
        Button btn_new_game = findViewById(R.id.btn_new_game);
        btn_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDifficultyDialog();
            }
        });

        Button btn_scores = findViewById(R.id.btn_score);
        btn_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreBoardActivity.class);
                startActivity(intent);
            }
        });

        Button btn_rate = findViewById(R.id.btn_rate);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });


        final ImageView btn_sound = findViewById(R.id.btn_sound);

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
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        if (!mSoundOn)
            mService.pauseMusic();
        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSoundOn) {
                    mSoundOn = false;
                    btn_sound.setImageResource(R.drawable.ic_sound_off);
                    if (mService != null)
                        mService.pauseMusic();
                } else {
                    mSoundOn = true;
                    btn_sound.setImageResource(R.drawable.ic_sound_on);
                    if (mService != null)
                        mService.resumeMusic();
                }
            }
        });
    }

    /**<----- Background Music ----->**/
    private ServiceConnection serviceConnection = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mService = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(serviceConnection);
            mIsBound = false;
        }
    }
    /**<----- Background Music ----->**/

    @Override
    protected void onResume() {
        super.onResume();

        /**<-------Hides the status bar------->**/
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**<-------Show the 'Resume Game' button if the user has a previous game------->**/
        mData = getSharedPreferences("score", MODE_PRIVATE);
        if (mData.getInt("user_score_1", 0) > 0) {
            btn_resume.setVisibility(View.VISIBLE);
        }

        /**<----- Background Music ----->**/
        if (mService != null && mSoundOn) {
            mService.resumeMusic();
        }
        /**<----- Background Music ----->**/
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**<----- Background Music ----->**/
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
        /**<----- Background Music ----->**/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**<----- Background Music ----->**/
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);
        mHomeWatcher.stopWatch();
        /**<----- Background Music ----->**/
    }


    void showDifficultyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_difficulty,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_easy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mData.edit();
                for (int i = 1; i <= 6; i++) {
                    editor.putInt("user_score_" + i, 0);
                }
                editor.commit();

                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", 1);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_medium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mData.edit();
                for (int i = 1; i <= 6; i++) {
                    editor.putInt("user_score_" + i, 0);
                }
                editor.commit();

                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", 2);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mData.edit();
                for (int i = 1; i <= 6; i++) {
                    editor.putInt("user_score_" + i, 0);
                }
                editor.commit();

                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", 3);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
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


    void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_rating,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);

        final RatingBar rating_bar = view.findViewById(R.id.rating_bar);
        final TextView rating_comment = view.findViewById(R.id.rating_comment);

        final AlertDialog alertDialog = builder.create();

        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_comment.setVisibility(View.VISIBLE);
                rating_comment.setText(getResources().getString(R.string.dlg_rating_comment_1) + " " + (int) (rating) + " " + getResources().getString(R.string.dlg_rating_comment_2));
            }
        });

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
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
}
