package com.app.yangyang.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.yangyang.zhbj.HomeActivity;
import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.base.impl.NewsCenterPager;
import com.app.yangyang.zhbj.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/4.
 */

public class LeftMenuFragment extends BaseFragment {

    @ViewInject(R.id.lv_list)
    private ListView  lv_list;

    private int mCurrentPos;


    private  ArrayList<NewsData.NewsMenuData> mNewsData;
    private MenuAdapter mMenuAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu,null);
        ViewUtils.inject(this,view);

        return view;
    }

    @Override
    public void initData() {

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;
                mMenuAdapter.notifyDataSetChanged();

                setCurrentMenuDetailPager(position);

                toggleSlidingMenu();

            }
        });




    }

    private void toggleSlidingMenu() {

        HomeActivity  mainUI = (HomeActivity) mActivity;

        SlidingMenu slidingMenu = mainUI.getSlidingMenu();

        slidingMenu.toggle();//切换状态


    }

    private void setCurrentMenuDetailPager(int position) {

        HomeActivity homeActivity = (HomeActivity) mActivity;

       ContentFragment contentFragment = homeActivity.getContentMenuFragment();

        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();

        newsCenterPager.setCurrentMenuDetailPager(position);
    }

    /**
     * 设置网络数据
     */
    public  void setMenuData(NewsData data){


        System.out.println( "拿到侧边栏数据："+data);

        mNewsData = data.data;


        mMenuAdapter = new MenuAdapter();
        lv_list.setAdapter(mMenuAdapter);


    }

    class MenuAdapter  extends BaseAdapter{


        @Override
        public int getCount() {
            return mNewsData.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return mNewsData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(mActivity, R.layout.list_menu_item, null);

            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);



            NewsData.NewsMenuData newsMenuData = getItem(position);

            tv_title.setText(newsMenuData.title);
            if(position == mCurrentPos){
                //显示红色
                tv_title.setEnabled(true);
            }else{
                //显示白色
                tv_title.setEnabled(false);
            }



            return view;
        }
    }
}
