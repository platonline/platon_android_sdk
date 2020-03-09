package com.platon.sample.views;

import android.content.Context;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class WrapContentHeightViewPager extends ViewPager {

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int pagerTitleStripHeight = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                // get the measuredHeight of the tallest fragment
                height = h;
            }
            if (child.getClass() == PagerTitleStrip.class) {
                // store the measured height of the pagerTitleStrip if one is found. This will only
                // happen if you have a android.support.v4.view.PagerTitleStrip as a direct child
                // of this class in your XML.
                pagerTitleStripHeight = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + pagerTitleStripHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
