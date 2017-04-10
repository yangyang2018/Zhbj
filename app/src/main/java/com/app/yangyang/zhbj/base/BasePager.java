package com.app.yangyang.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.yangyang.zhbj.HomeActivity;
import com.app.yangyang.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by yangyang on 2017/4/6.
 */

public class BasePager {

    public  Activity mActivity;
    public View mRootView;

    public TextView  tv_title;

    public FrameLayout  fl_content;

    public ImageButton btn_menu;

    public BasePager(Activity  context) {
        this.mActivity = context;
        initView();
    }

    public   void  initView(){

        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        btn_menu = (ImageButton) mRootView.findViewById(R.id.btn_menu);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });

    }

    private void toggleSlidingMenu() {

        HomeActivity  mainUI = (HomeActivity) mActivity;

        SlidingMenu slidingMenu = mainUI.getSlidingMenu();

        slidingMenu.toggle();//切换状态


    }


    public   void  initData(){

    }

    public   void  setSlidingMenuEnable(boolean  enable){

        HomeActivity homeUI = (HomeActivity) mActivity;
        SlidingMenu slidingMenu = homeUI.getSlidingMenu();
        if(enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }


    }
}
