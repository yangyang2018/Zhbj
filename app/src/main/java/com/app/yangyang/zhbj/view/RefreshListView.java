package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.app.yangyang.zhbj.R;

/**
 * Created by yangyang on 2017/4/23.
 */

public class RefreshListView extends ListView {

    private int mMeasureHeight;


    private int startY = -1;
    private View mHeaderView;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();

    }

    /**
    * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header,null);

        this.addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0,0);
        mMeasureHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mMeasureHeight,0,0);




    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                 startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //避免有时action down 没有戳发 startY 没有值
                if(startY == -1){
                    startY = (int) ev.getRawY();
                }
                int endY = (int) ev.getRawY();
                //移动偏移量
                int dy = endY -startY;

                if(dy > 0 && getFirstVisiblePosition() ==0){


                    int padding = dy - mMeasureHeight;

                    mHeaderView.setPadding(0,padding,0,0);
                    return  true;

                }



                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//  重制



                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);


    }
}
