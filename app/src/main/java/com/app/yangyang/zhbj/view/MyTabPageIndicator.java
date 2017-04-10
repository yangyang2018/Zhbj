package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by yangyang on 2017/4/9.
 */

public class MyTabPageIndicator extends TabPageIndicator {


    public MyTabPageIndicator(Context context) {
        super(context);
    }

    public MyTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
