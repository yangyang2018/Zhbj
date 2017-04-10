package com.app.yangyang.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.app.yangyang.zhbj.fragment.ContentFragment;
import com.app.yangyang.zhbj.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 用户 - 主页面
 */
public class HomeActivity extends SlidingFragmentActivity {

    public   static final String  FRAGMENT_LEFT_MENU = "fragment_left_menu";
    public   static final String  FRAGMENT_CONTENT_MENU = "fragment_content_menu";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(500);
        initFragment();
    }


    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.fl_content_menu, new ContentFragment(),FRAGMENT_CONTENT_MENU);

        transaction.commit();


    }


    public  LeftMenuFragment  getLeftMenuFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) fragmentManager.findFragmentByTag(FRAGMENT_LEFT_MENU);


        return  leftMenuFragment;

    }

    public  ContentFragment  getContentMenuFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        ContentFragment contentFragment = (ContentFragment) fragmentManager.findFragmentByTag(FRAGMENT_CONTENT_MENU);


        return  contentFragment;

    }
}
