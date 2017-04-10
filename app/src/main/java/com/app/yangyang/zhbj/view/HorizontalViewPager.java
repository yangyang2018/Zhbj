package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 弃用
 *
 * 11 个页签水平滑动左滑不要拦截
 * Created by yangyang on 2017/4/9.
 */
@Deprecated
public class HorizontalViewPager extends ViewPager {
    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()!=0){

             getParent().requestDisallowInterceptTouchEvent(true);
        }else{
            //第一个页签需要显示侧边栏
             getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }
}
