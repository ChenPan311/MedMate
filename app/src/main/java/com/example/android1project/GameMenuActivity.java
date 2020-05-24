package com.example.android1project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class GameMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        final ImageButton option1 = findViewById(R.id.option_1);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option1Preview.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option1.setAlpha(1f);
                    }
                }, 250);
            }
        });

        final ImageButton option2 = findViewById(R.id.option_2);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option2Preview.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option2.setAlpha(1f);
                    }
                }, 250);
            }
        });

        final ImageButton option3 = findViewById(R.id.option_3);
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option3.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option3Preview.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option3.setAlpha(1f);
                    }
                }, 250);
            }
        });

        final ImageButton option4 = findViewById(R.id.option_4);
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option4.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option4Preview.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option4.setAlpha(1f);
                    }
                }, 250);
            }
        });

        final ImageButton option5 = findViewById(R.id.option_5);
        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option5.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option5Activity.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option5.setAlpha(1f);
                    }
                }, 250);
            }
        });

        final ImageButton option6 = findViewById(R.id.option_6);
        option6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option6.setAlpha(0.25f);
                Intent intent = new Intent(GameMenuActivity.this, Option6Activity.class);
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
}
