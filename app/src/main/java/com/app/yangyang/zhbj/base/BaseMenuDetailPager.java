package com.app.yangyang.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by yangyang on 2017/4/9.
 */

public abstract  class BaseMenuDetailPager {

    public Activity  mActivity;


    public View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        this.mActivity = activity;

        mRootView = initView();
    }

    public abstract View initView();

    public  void initData(){

    }

}
