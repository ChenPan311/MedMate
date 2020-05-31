package com.example.android1project;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ScoreBoardActivity extends ListActivity {
    SharedPreferences mData;
    ArrayList<UserInfo> mUsers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        mData = getSharedPreferences("score", MODE_PRIVATE);

        for (int i = 0; i < 20; i++) {
            mUsers.add(new UserInfo("Chenko", 3000 + i));
        }


        final ArrayAdapter<UserInfo> adapter = new ArrayAdapter<>(this, R.layout.score_cell, R.id.tv, mUsers);
        setListAdapter(adapter);


    }
}
