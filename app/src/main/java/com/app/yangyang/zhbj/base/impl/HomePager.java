package com.app.yangyang.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.app.yangyang.zhbj.base.BasePager;

/**
 * Created by yangyang on 2017/4/6.
 */

public class HomePager extends BasePager {
    public  static  final  String TAG = "HOMEPAGER";

    public HomePager(Activity context) {
        super(context);
    }

    @Override
    public void initData() {
        Log.d(TAG,"初始化首页数据");
        tv_title.setText("智慧北京");
        btn_menu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);



        TextView  textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        fl_content.addView(textView);



    }



}
