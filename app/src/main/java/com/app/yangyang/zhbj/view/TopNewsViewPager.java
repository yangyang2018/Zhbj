package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yangyang on 2017/4/6.
 */

public class TopNewsViewPager extends ViewPager {

    private int startX;
    private int endX;
    private int startY;
    private int endY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 1,右划，而且是第一个页面，希望父控件拦截
     *
     * 2,左划，而且是最后一个页面，需要父控件拦截
     *
     * 3,上下划的时候，需要父控件处理
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                startX = (int) event.getRawX();
                startY = (int) event.getRawY();




                break;
            case MotionEvent.ACTION_MOVE:

                endX = (int) event.getRawX();
                endY = (int) event.getRawY();

                if(Math.abs(endX - startX) > Math.abs(endY - startY)){
                    //表示左右划动
                    if (endX>startX) {
                        //右划 第一个拦截
                        if(getCurrentItem() == 0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                    }else {
                        //左划 最后一个拦截
                        if(getCurrentItem() == getAdapter().getCount()-1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                    }



                }else{
                    //上下划动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }


                break;

            default:

                break;


        }

        return super.dispatchTouchEvent(event);
    }
}
