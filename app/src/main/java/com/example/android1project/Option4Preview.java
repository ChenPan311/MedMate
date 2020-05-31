package com.example.android1project;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Option4Preview extends AppCompatActivity {
    private float mDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_4);

        final int difficulty = getIntent().getIntExtra("difficulty", 1);

        mDensity = getResources().getDisplayMetrics().density;

        final ImageView victim = findViewById(R.id.victim_4);

        final ImageView magnifier = findViewById(R.id.magnifier4);

        final ImageButton research_btn = findViewById(R.id.research_btn4);
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
                        if (checkCollision(magnifier, victim)) {
                            Intent intent = new Intent(Option4Preview.this, Option4Activity.class);
                            intent.putExtra("difficulty", difficulty);
                            finish();
                            startActivity(intent);
                        }
                        break;
                }
                v.requestLayout();
                return true;
            }
        });
    }

    public boolean checkCollision(View tool, View object) {
        Rect R1 = new Rect(tool.getLeft(), tool.getTop(), tool.getLeft() + (int) (90 * mDensity), tool.getTop() + (int) (90 * mDensity));
        Rect R2 = new Rect(object.getRight() - (int) (30 * mDensity), object.getBottom() - (int) (190 * mDensity), object.getRight(), object.getBottom() - (int) (160 * mDensity));
        return R1.intersect(R2);
    }
}
