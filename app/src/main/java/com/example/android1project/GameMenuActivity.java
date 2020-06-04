package com.example.android1project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class GameMenuActivity extends AppCompatActivity {
    private SharedPreferences mData;

    private ImageButton option1;
    private ImageButton option2;
    private ImageButton option3;
    private ImageButton option4;
    private ImageButton option5;
    private ImageButton option6;

    private int difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        difficulty = getIntent().getIntExtra("difficulty", 1);

        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        option5 = findViewById(R.id.option_5);
        option6 = findViewById(R.id.option_6);

        myOnResume();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option1Preview.class);
                intent.putExtra("difficulty", difficulty);
                if (mData.getInt("user_score_1", 0) == 0)
                    intent.putExtra("guidance", true);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option1.setAlpha(1f);
                    }
                }, 250);
            }
        });


        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option2Preview.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option2.setAlpha(1f);
                    }
                }, 250);
            }
        });


        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option3.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option3Preview.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option3.setAlpha(1f);
                    }
                }, 250);
            }
        });


        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option4.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option4Preview.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option4.setAlpha(1f);
                    }
                }, 250);
            }
        });


        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option5.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option5Activity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option5.setAlpha(1f);
                    }
                }, 250);
            }
        });


        option6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option6.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option6Activity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option6.setAlpha(1f);
                    }
                }, 250);
            }
        });
    }

    void myOnResume() {
        mData = getSharedPreferences("score", MODE_PRIVATE);

        mData.edit().putInt("difficulty", difficulty).commit();

        option2.setEnabled(false);
        option2.setImageResource(R.drawable.ic_girl_1_bw);
        if (mData.getInt("user_score_1", 0) > 0) {
            option1.setImageResource(R.drawable.ic_boy_1_done);
            option2.setImageResource(R.drawable.ic_girl_1);
            option2.setEnabled(true);
        }

        option3.setEnabled(false);
        option3.setImageResource(R.drawable.ic_boy_2_bw);
        if (mData.getInt("user_score_2", 0) > 0) {
            option2.setImageResource(R.drawable.ic_girl_1_done);
            option3.setImageResource(R.drawable.ic_boy_2);
            option3.setEnabled(true);
        }

        option4.setEnabled(false);
        option4.setImageResource(R.drawable.ic_girl_2_bw);
        if (mData.getInt("user_score_3", 0) > 0) {
            option3.setImageResource(R.drawable.ic_boy_2_done);
            option4.setImageResource(R.drawable.ic_girl_2);
            option4.setEnabled(true);
        }

        option5.setEnabled(false);
        option5.setImageResource(R.drawable.ic_boy_3_bw);
        if (mData.getInt("user_score_4", 0) > 0) {
            option4.setImageResource(R.drawable.ic_girl_2_done);
            option5.setImageResource(R.drawable.ic_boy_3);
            option5.setEnabled(true);
        }

        option6.setEnabled(false);
        option6.setImageResource(R.drawable.ic_girl_3_bw);
        if (mData.getInt("user_score_5", 0) > 0) {
            option5.setImageResource(R.drawable.ic_boy_3_done);
            option6.setImageResource(R.drawable.ic_girl_3);
            option6.setEnabled(true);
        }

        if (mData.getInt("user_score_6", 0) > 0) {
            option6.setImageResource(R.drawable.ic_girl_3_done);
        }
    }

    @Override
    protected void onResume() {
        myOnResume();
        super.onResume();
    }
}
