package com.app.yangyang.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.app.yangyang.zhbj.base.BasePager;

/**
 * Created by yangyang on 2017/4/6.
 */

public class GovAffairsPager extends BasePager {

    public GovAffairsPager(Activity context) {
        super(context);
    }

    @Override
    public void initData() {
        tv_title.setText("智慧北京");
        setSlidingMenuEnable(true);

        TextView  textView = new TextView(mActivity);
        textView.setText("政务");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        fl_content.addView(textView);



    }
}
