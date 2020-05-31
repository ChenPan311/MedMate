package com.example.android1project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private boolean mSoundOn = true;
    private boolean mIsBound = false;
    private MusicService mService;
    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_resume = findViewById(R.id.btn_resume_game);
        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                startActivity(intent);
            }
        });

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(R.string.rate_us + "!");
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
                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", 1);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_medium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameMenuActivity.class);
                intent.putExtra("difficulty", 2);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
