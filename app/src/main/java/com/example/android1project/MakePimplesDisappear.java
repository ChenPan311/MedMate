package com.example.android1project;

import android.os.Handler;
import android.view.View;

class MakePimplesDisappear {
    private boolean isActive = false;
    private View view;
    private long milliseconds;

    MakePimplesDisappear(View view, long milliseconds) {
        this.view = view;
        this.milliseconds = milliseconds;
    }

    void makePimpleDisappear() {
        isActive = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAlpha(view.getAlpha() > 0 ? view.getAlpha() - 0.05f : 0);
                isActive = false;
            }
        }, milliseconds);
    }

    boolean isActive() {
        return isActive;
    }
}
