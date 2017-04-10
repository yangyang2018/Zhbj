package com.app.yangyang.zhbj.fragment;

import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.base.BasePager;
import com.app.yangyang.zhbj.base.impl.GovAffairsPager;
import com.app.yangyang.zhbj.base.impl.HomePager;
import com.app.yangyang.zhbj.base.impl.NewsCenterPager;
import com.app.yangyang.zhbj.base.impl.SettingPager;
import com.app.yangyang.zhbj.base.impl.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/4.
 */

public class ContentFragment extends BaseFragment {


    @ViewInject(R.id.rg_group)
    private RadioGroup rg_group;
    @ViewInject(R.id.vp_content)
    private ViewPager vp_content;

    private ArrayList<BasePager>  mPagerList;

    @Override
    public View initView() {
        View  view = View.inflate(mActivity, R.layout.fragment_content_menu,null);


        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        rg_group.check(R.id.rb_home);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {

                    case R.id.rb_home:

                        vp_content.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        vp_content.setCurrentItem(1,false);

                        break;
                    case R.id.rb_smart:
                        vp_content.setCurrentItem(2,false);

                        break;
                    case R.id.rb_gov:
                        vp_content.setCurrentItem(3,false);

                        break;
                    case R.id.rb_setting:
                        vp_content.setCurrentItem(4,false);

                        break;
                }


            }
        });

        mPagerList = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            BasePager pager = new BasePager(mActivity);
//            mPagerList.add(pager);
//        }
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));


        vp_content.setAdapter(new ContentAdapter());
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //手动初始化第一个页面
        mPagerList.get(0).initData();

    }


    class ContentAdapter  extends PagerAdapter {


        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);

//            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    public  NewsCenterPager  getNewsCenterPager(){

        return (NewsCenterPager) mPagerList.get(1);
    }






}
