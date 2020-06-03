package com.example.android1project;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MedKit extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private Activity mContext;
    private PopupWindow mPopupWindow;
    RelativeLayout mViewGroup;
    LayoutInflater mLayoutInflater;
    ImageView item;
    View mLayout;

    private int mToolId;

    private boolean mIsTweezers, mIsBandAid, mIsOintment, mIsEpipen, mIsPen, mIsDefibrillator;

    private float mDensity;

    public MedKit(Context context) {
        super(context);
        mDensity = getResources().getDisplayMetrics().density;
    }

    public MedKit(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDensity = getResources().getDisplayMetrics().density;

        this.mContext = (Activity) context;
        mViewGroup = this.mContext.findViewById(R.id.medKit_popup);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = mLayoutInflater.inflate(R.layout.medkit_popup_window, mViewGroup);

        //Creating the Popup Window:
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setContentView(mLayout);
        mPopupWindow.setFocusable(true);

        //Clear the default translucent background:
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.setAnimationStyle(R.style.MedKitPopupWindowAnimation);
    }

    @Override
    public void onClick(View v) {
        //Some offset to align the popup a bit to the right, and a bit down, relative to button's position:
        int OFFSET_X = (int) (200 * mDensity);
        int OFFSET_Y = (int) (300 * mDensity);

        // Displaying the popup at the specified location, + offsets:
        mPopupWindow.showAtLocation(mLayout, Gravity.NO_GRAVITY, (int) this.getX() - OFFSET_X, (int) this.getY() - OFFSET_Y);
        item = this.mContext.findViewById(mToolId);

        ImageView tweezers = mLayout.findViewById(R.id.tweezers);
        ImageView band_aid = mLayout.findViewById(R.id.bandaid);
        ImageView ointment = mLayout.findViewById(R.id.ointment);
        ImageView epipen = mLayout.findViewById(R.id.epipen);
        ImageView pen = mLayout.findViewById(R.id.pen);

        tweezers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_tweezers_open);
                item.setVisibility(VISIBLE);
                mPopupWindow.dismiss();

                mIsTweezers = true;
                mIsBandAid = mIsOintment = mIsEpipen = mIsDefibrillator = mIsPen = false;
            }
        });

        band_aid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_band_aid);
                item.setVisibility(VISIBLE);
                mPopupWindow.dismiss();

                mIsBandAid = true;
                mIsTweezers = mIsOintment = mIsEpipen = mIsDefibrillator = mIsPen = false;
            }
        });

        ointment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_ointment);
                item.setVisibility(VISIBLE);
                mPopupWindow.dismiss();

                mIsOintment = true;
                mIsTweezers = mIsBandAid = mIsEpipen = mIsDefibrillator = mIsPen = false;
            }
        });

        epipen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_epipen);
                item.setVisibility(VISIBLE);
                mPopupWindow.dismiss();

                mIsEpipen = true;
                mIsTweezers = mIsBandAid = mIsOintment = mIsDefibrillator = mIsPen = false;
            }
        });

        pen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_pen);
                item.setVisibility(VISIBLE);
                mPopupWindow.dismiss();

                mIsPen = true;
                mIsTweezers = mIsBandAid = mIsOintment = mIsEpipen = mIsDefibrillator = false;
            }
        });
    }

    public void setItemId(final int toolId) {
        this.mToolId = toolId;
    }

    public boolean isTweezers() {
        return mIsTweezers;
    }

    public void setIsTweezers(boolean isTweezers) {
        this.mIsTweezers = isTweezers;
    }

    public boolean isBandAid() {
        return mIsBandAid;
    }

    public void setIsBandAid(boolean isBandAid) {
        this.mIsBandAid = isBandAid;
    }

    public boolean isOintment() {
        return mIsOintment;
    }

    public void setIsOintment(boolean isOintment) {
        this.mIsOintment = isOintment;
    }

    public boolean isEpipen() {
        return mIsEpipen;
    }

    public void setIsEpipen(boolean isEpipen) {
        this.mIsEpipen = isEpipen;
    }

    public boolean isPen() {
        return mIsPen;
    }

    public void setIsPen(boolean isPen) {
        this.mIsPen = isPen;
    }

    public boolean isDefibrillator() {
        return mIsDefibrillator;
    }

    public void setIsDefibrillator(boolean isDefibrillator) {
        this.mIsDefibrillator = isDefibrillator;
    }

    public void DismissWindow() {
        if(mPopupWindow != null)
            mPopupWindow.dismiss();
    }
}
