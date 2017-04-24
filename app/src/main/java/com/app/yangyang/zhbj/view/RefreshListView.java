package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.app.yangyang.zhbj.R;

/**
 * Created by yangyang on 2017/4/23.
 */

public class RefreshListView extends ListView {
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
        View  mHeaderView = View.inflate(getContext(), R.layout.refresh_header,null);

        this.addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0,0);
        int mMeasureHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mMeasureHeight,0,0);




    }
}
