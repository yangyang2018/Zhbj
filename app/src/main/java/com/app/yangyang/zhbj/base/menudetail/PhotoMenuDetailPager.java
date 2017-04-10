package com.app.yangyang.zhbj.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.app.yangyang.zhbj.base.BaseMenuDetailPager;

/**
 * Created by yangyang on 2017/4/9.
 */

public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    public PhotoMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        TextView textView =  new TextView(mActivity);
        textView.setText("菜单详情页-组图");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
