package com.example.android1project;

import android.graphics.Color;

public class Circle {
    private float mX;
    private float mY;
    private float mRadius;
    private int mColor;
    private int mAlpha = 64;

    Circle(float mX, float mY) {
        this.mX = mX;
        this.mY = mY;
        mColor = Color.argb(mAlpha, 169, 245, 255);
    }

    public float getX() {
        return mX;
    }

    public void setX(float mX) {
        this.mX = mX;
    }

    public float getY() {
        return mY;
    }

    public void setY(float mY) {
        this.mY = mY;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public float getAlpha() {
        return (float) ((mAlpha) / 256);
    }

    public void setAlpha(float mAlpha) {
        this.mAlpha = (int) (mAlpha * 256);
        this.mColor = Color.argb(this.mAlpha, 169, 245, 255);
    }
}
