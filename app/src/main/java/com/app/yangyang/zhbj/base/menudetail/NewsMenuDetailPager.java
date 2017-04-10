package com.app.yangyang.zhbj.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.app.yangyang.zhbj.HomeActivity;
import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.base.BaseMenuDetailPager;
import com.app.yangyang.zhbj.base.TabDetailPager;
import com.app.yangyang.zhbj.domain.NewsData;
import com.app.yangyang.zhbj.view.MyTabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/9.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {

    private ViewPager vp_menu_detail;

    private ArrayList<NewsData.NewsTypeData> mNewsTabData;

    public ArrayList<TabDetailPager> mTabDetailPagerList;
    private MyTabPageIndicator indicator;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTypeData> children) {
        super(activity);
        this.mNewsTabData = children;
    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);

        vp_menu_detail = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        ViewUtils.inject(this, view);

        //初始化viewpager指示器
        indicator = (MyTabPageIndicator) view.findViewById(R.id.indicator);

        indicator.setOnPageChangeListener(this);

        return view;
    }

    @Override
    public void initData() {

        mTabDetailPagerList = new ArrayList<>();

        for (NewsData.NewsTypeData data : mNewsTabData) {
            TabDetailPager pg = new TabDetailPager(mActivity, data);
            mTabDetailPagerList.add(pg);
        }

        vp_menu_detail.setAdapter(new MenuDetailAdapter());
        //需在viewPager设置完adapter 之后
        indicator.setViewPager(vp_menu_detail);
    }

    @OnClick(R.id.btn_next)
    public void nextPage(View view) {
        int currentPager = vp_menu_detail.getCurrentItem();
        vp_menu_detail.setCurrentItem(++currentPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        System.out.println("选中页面"+position);

        HomeActivity homeActivity = (HomeActivity) mActivity;

        SlidingMenu slidingMenu = homeActivity.getSlidingMenu();

        if (position == 0) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MenuDetailAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }

        @Override
        public int getCount() {
            return mTabDetailPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TabDetailPager pager = mTabDetailPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }
    }


}
