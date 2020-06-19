package com.example.android1project;

import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardActivity extends ListActivity {
    private boolean mIsBound = false;
    private MusicService mService;
    private HomeWatcher mHomeWatcher;

    ArrayAdapter<UserInfo> adapter;

    SharedPreferences mData;
    SharedPreferences mUserInfo;
    ArrayList<UserInfo> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);


        /**<-------Hides the status bar------->**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mData = getSharedPreferences("score", MODE_PRIVATE);
        mUserInfo = getSharedPreferences("users", MODE_PRIVATE);

        /**<-------if the user completed the game ask him on if he wants to save his score------->*/
        boolean game_completed = getIntent().getBooleanExtra("game_completed", false);
        if (game_completed) {
            showSaveScoreDialog();
        }


        int size = mUserInfo.getInt("size", 0);

        /**<-------getting the all the user's info from the device and put it in an array------->*/
        for (int i = 1; i <= size; i++) {
            mUsers.add(new UserInfo(mUserInfo.getString("userName_" + i, "Unknown"), mUserInfo.getInt("userScore_" + i, 0)));
        }

        Collections.sort(mUsers);


        adapter = new ArrayAdapter<>(this, R.layout.score_cell, R.id.tv, mUsers);
        setListAdapter(adapter);


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


    void showSaveScoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreBoardActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ScoreBoardActivity.this).inflate(R.layout.dialog_save_score,
                (RelativeLayout) findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        builder.setCancelable(false);

        final EditText editText = view.findViewById(R.id.user_name_et);
        final ImageButton btn_cancel = view.findViewById(R.id.btn_cancel);
        final ImageButton btn_confirm = view.findViewById(R.id.btn_confirm);

        final AlertDialog alertDialog = builder.create();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**<-------Saves the user's score------->*/
                int size = mUserInfo.getInt("size", 0);
                size++;

                SharedPreferences.Editor editor = mUserInfo.edit();

                editor.putInt("size", size);

                String user_name = editText.getText().toString().trim().length() > 0 ?
                        editText.getText().toString() : "Unknown";
                editor.putString("userName_" + size, user_name);

                int user_score = mData.getInt("final_score", 0);
                editor.putInt("userScore_" + size, user_score);

                editor.commit();

                mUsers.add(new UserInfo(user_name, user_score));
                Collections.sort(mUsers);
                adapter.notifyDataSetChanged();

                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
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
