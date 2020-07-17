package com.example.android1project;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MedKit extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private Activity mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout mViewGroup;
    private LayoutInflater mLayoutInflater;
    private ImageView item;
    private View mLayout;

    private int mToolId;
    private boolean isFirstItemsPick = true;

    private boolean isFirstEpipenUse = false;

    private boolean mIsTweezers, mIsBandAid, mIsOintment, mIsEpipen, mIsPen, mIsDefibrillator;

    private boolean firstLevelGuide = false, allLevelGuide = false;
    private HealthBar mHp;

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
        int OFFSET_Y = (int) (310 * mDensity);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final int screenHeight = displayMetrics.heightPixels;
        final int screenWidth = displayMetrics.widthPixels;

        // Displaying the popup at the specified location, + offsets:
        mPopupWindow.showAtLocation(mLayout, Gravity.NO_GRAVITY, (int) this.getX() - OFFSET_X, (int) this.getY() - OFFSET_Y);
        item = this.mContext.findViewById(mToolId);

        ImageView tweezers = mLayout.findViewById(R.id.tweezers);
        ImageView band_aid = mLayout.findViewById(R.id.bandaid);
        ImageView ointment = mLayout.findViewById(R.id.ointment);
        final ImageView epipen = mLayout.findViewById(R.id.epipen);
        ImageView pen = mLayout.findViewById(R.id.pen);

        final TextView guideTv = this.mContext.findViewById(R.id.guide_tv);

        tweezers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_tweezers_open);
                //For some reason the item doesn't get the width and height it should get, so when trying to
                //locate the item on the center of the screen - it can't be done bc the width and height of the
                //item are needed (it just stays 0)...So i'm using this instead:
                item.setMaxWidth(getResources().getDrawable(R.drawable.ic_tweezers_open).getIntrinsicWidth());
                item.setMaxHeight(getResources().getDrawable(R.drawable.ic_tweezers_open).getIntrinsicHeight());
                item.setVisibility(VISIBLE);

                mIsTweezers = true;
                mIsBandAid = mIsOintment = mIsEpipen = mIsDefibrillator = mIsPen = false;

                mPopupWindow.dismiss();
            }
        });

        band_aid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_band_aid);
                item.setMaxWidth(getResources().getDrawable(R.drawable.ic_band_aid).getIntrinsicWidth());
                item.setMaxHeight(getResources().getDrawable(R.drawable.ic_band_aid).getIntrinsicHeight());
                item.setVisibility(VISIBLE);

                mIsBandAid = true;
                mIsTweezers = mIsOintment = mIsEpipen = mIsDefibrillator = mIsPen = false;

                mPopupWindow.dismiss();
            }
        });

        ointment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_ointment);
                item.setMaxWidth(getResources().getDrawable(R.drawable.ic_ointment).getIntrinsicWidth());
                item.setMaxHeight(getResources().getDrawable(R.drawable.ic_ointment).getIntrinsicHeight());
                item.setVisibility(VISIBLE);

                mIsOintment = true;
                mIsTweezers = mIsBandAid = mIsEpipen = mIsDefibrillator = mIsPen = false;

                mPopupWindow.dismiss();
            }
        });

        epipen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_epipen);
                item.setMaxWidth(getResources().getDrawable(R.drawable.ic_epipen).getIntrinsicWidth());
                item.setMaxHeight(getResources().getDrawable(R.drawable.ic_epipen).getIntrinsicHeight());
                item.setVisibility(VISIBLE);

                if (isFirstEpipenUse) {
                    final TextView epipen_guide = mContext.findViewById(R.id.epipen_guide);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(600);
                    final ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation2.setDuration(300);
                    epipen_guide.setAnimation(scaleAnimation);
                    epipen_guide.setVisibility(VISIBLE);
                    epipen_guide.startAnimation(scaleAnimation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            epipen_guide.clearAnimation();
                            epipen_guide.setAnimation(scaleAnimation2);
                            epipen_guide.startAnimation(scaleAnimation2);
                            epipen_guide.setVisibility(View.GONE);
                        }
                    }, 5000);
                }

                mIsEpipen = true;
                mIsTweezers = mIsBandAid = mIsOintment = mIsDefibrillator = mIsPen = false;

                mPopupWindow.dismiss();
            }
        });

        pen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setImageResource(R.drawable.ic_pen);
                item.setMaxWidth(getResources().getDrawable(R.drawable.ic_pen).getIntrinsicWidth());
                item.setMaxHeight(getResources().getDrawable(R.drawable.ic_pen).getIntrinsicHeight());
                item.setVisibility(VISIBLE);

                mIsPen = true;
                mIsTweezers = mIsBandAid = mIsOintment = mIsEpipen = mIsDefibrillator = false;

                mPopupWindow.dismiss();
            }
        });

        if (firstLevelGuide || allLevelGuide) {
            if (firstLevelGuide) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(600);
                guideTv.setAnimation(scaleAnimation);
                guideTv.setVisibility(View.VISIBLE);
                guideTv.startAnimation(scaleAnimation);
            }

            Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_out);
            final ImageView ekg_btn_guide = mLayout.findViewById(R.id.ekg_btn_guide);
            ekg_btn_guide.setVisibility(VISIBLE);
            ekg_btn_guide.startAnimation(anim);
            final ImageView help_btn_guide = mLayout.findViewById(R.id.help_btn_guide);
            help_btn_guide.setVisibility(VISIBLE);
            help_btn_guide.startAnimation(anim);
            final ImageView book_guide = mLayout.findViewById(R.id.book_guide);
            book_guide.setVisibility(VISIBLE);
            book_guide.startAnimation(anim);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ekg_btn_guide.clearAnimation();
                    help_btn_guide.clearAnimation();
                    book_guide.clearAnimation();
                    ekg_btn_guide.setVisibility(View.GONE);
                    help_btn_guide.setVisibility(View.GONE);
                    book_guide.setVisibility(View.GONE);
                }
            }, 5000);
        }

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (firstLevelGuide) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    guideTv.setAnimation(scaleAnimation);
                    guideTv.setVisibility(View.GONE);
                    guideTv.startAnimation(scaleAnimation);
                    firstLevelGuide = false;

                    Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_out);
                    final ImageView hp_guide = mContext.findViewById(R.id.hp_guide);
                    hp_guide.setVisibility(View.VISIBLE);
                    hp_guide.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hp_guide.clearAnimation();
                            hp_guide.setVisibility(View.GONE);
                        }
                    }, 3000);

                    if (mHp != null)
                        mHp.resume();
                }

                /**<-------if the user picked any item for the first time
                 *              locate it in the center of the screen------->*/
                if (isFirstItemsPick && (mIsTweezers || mIsBandAid || mIsOintment || mIsEpipen || mIsPen)) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) item.getLayoutParams();
                    layoutParams.leftMargin = (screenWidth - item.getMaxWidth()) / 2;
                    layoutParams.topMargin = (screenHeight - item.getMaxHeight()) / 2;
                    isFirstItemsPick = false;
                }
            }
        });
    }

    public View getLayout() {
        return mLayout;
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

    public void setFirstLevelGuide(boolean firstLevelGuide) {
        this.firstLevelGuide = firstLevelGuide;
    }

    public void setAllLevelGuide(boolean allLevelGuide) {
        this.allLevelGuide = allLevelGuide;
    }

    public void setFirstEpipenUse(boolean firstEpipenUse) {
        isFirstEpipenUse = firstEpipenUse;
    }

    public void setHealthBar(HealthBar mHp) {
        this.mHp = mHp;
    }

    public void DismissWindow() {
        if(mPopupWindow != null)
            mPopupWindow.dismiss();
    }
}
