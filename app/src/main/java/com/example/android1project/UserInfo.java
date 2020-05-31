package com.example.android1project;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String mName;
    private int mScore;

    public UserInfo(String name, int score) {
        this.mName = name;
        this.mScore = score;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    @Override
    public String toString() {
        return mName + " \t " + mScore;
    }
}
