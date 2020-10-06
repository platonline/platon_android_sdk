package com.platon.sdk.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.platon.R;

public class GooglePayButton extends RelativeLayout {

    private boolean isShadow, isBlack, isWithText;

    public GooglePayButton(Context context) {
        super(context);
        init(context, null);
    }

    public GooglePayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GooglePayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.GooglePayButton);
        isShadow = a.getBoolean(R.styleable.GooglePayButton_shadow, false);
        isBlack = a.getBoolean(R.styleable.GooglePayButton_black, false);
        isWithText = a.getBoolean(R.styleable.GooglePayButton_withText, false);
        a.recycle();
        setLayout(context);
    }

    private void setLayout(Context context) {
        int layout;
        if (isBlack) {
            if (isWithText) {
                layout = R.layout.buy_with_googlepay_button_black;
            } else {
                layout = R.layout.googlepay_button_black;
            }
        } else {
            if (isShadow) {
                if (isWithText) {
                    layout = R.layout.buy_with_googlepay_button_white;
                } else {
                    layout = R.layout.googlepay_button_white;
                }
            } else {
                if (isWithText) {
                    layout = R.layout.buy_with_googlepay_button_no_shadow_white;
                } else {
                    layout = R.layout.googlepay_button_no_shadow_white;
                }
            }
        }
        removeAllViews();
        LayoutInflater.from(context).inflate(layout, this, true);
    }

    public void setBlack(boolean isBlack) {
        this.isBlack = isBlack;
        setLayout(getContext());
        invalidate();
    }

    public void setShadow(boolean isShadow) {
        this.isShadow = isShadow;
        setLayout(getContext());
        invalidate();
    }

    public void setWithText(boolean isWithText) {
        this.isWithText = isWithText;
        setLayout(getContext());
        invalidate();
    }
}
