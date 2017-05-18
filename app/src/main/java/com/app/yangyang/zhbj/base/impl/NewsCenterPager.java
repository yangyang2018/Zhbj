package com.app.yangyang.zhbj.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.app.yangyang.zhbj.HomeActivity;
import com.app.yangyang.zhbj.base.BaseMenuDetailPager;
import com.app.yangyang.zhbj.base.BasePager;
import com.app.yangyang.zhbj.base.menudetail.InteractMenuDetailPager;
import com.app.yangyang.zhbj.base.menudetail.NewsMenuDetailPager;
import com.app.yangyang.zhbj.base.menudetail.PhotoMenuDetailPager;
import com.app.yangyang.zhbj.base.menudetail.TopicMenuDetailPager;
import com.app.yangyang.zhbj.domain.NewsData;
import com.app.yangyang.zhbj.fragment.LeftMenuFragment;
import com.app.yangyang.zhbj.global.GlobalContants;
import com.app.yangyang.zhbj.util.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/6.
 */

public class NewsCenterPager extends BasePager {
    private static final String TAG = "NEWSCENTERPAGER";

    private ArrayList<BaseMenuDetailPager> menuDetailPagers;
    private NewsData newsData;


    public NewsCenterPager(Activity context) {
        super(context);
    }

    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORIES_URL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("返回结果：" + result);
                parseData(result);
                //put data into  cache
                CacheUtils.setCache(GlobalContants.CATEGORIES_URL,result,mActivity);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initData() {
        Log.d(TAG, "初始化新闻中心数据");
        tv_title.setText("新闻");
        setSlidingMenuEnable(true);
//
//        TextView  textView = new TextView(mActivity);
//        textView.setText("新闻中心");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        textView.setGravity(Gravity.CENTER);

//        fl_content.addView(textView);

        //get data from cache
        String value = CacheUtils.getCache(GlobalContants.CATEGORIES_URL, mActivity);
        if (!TextUtils.isEmpty(value)) {
            parseData(value);
        }
        //slient get data from server
        getDataFromServer();



    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void parseData(String result) {
        Gson gson = new Gson();

        newsData = gson.fromJson(result, NewsData.class);

        System.out.println("解析结果" + newsData);

        HomeActivity homeActivity = (HomeActivity) mActivity;

        LeftMenuFragment leftMenuFragment = homeActivity.getLeftMenuFragment();

        leftMenuFragment.setMenuData(newsData);

        //设置4个菜单详情页数据
        menuDetailPagers = new ArrayList<>();
        menuDetailPagers.add(new NewsMenuDetailPager(mActivity, newsData.data.get(0).children));
        menuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        menuDetailPagers.add(new PhotoMenuDetailPager(mActivity));
        menuDetailPagers.add(new InteractMenuDetailPager(mActivity));

        setCurrentMenuDetailPager(0);
    }

    /**
     * 设置菜单详情页
     *
     * @param position
     */
    public void setCurrentMenuDetailPager(int position) {

        BaseMenuDetailPager menuDetailPager = menuDetailPagers.get(position);

        fl_content.removeAllViews();

        fl_content.addView(menuDetailPager.mRootView);


        tv_title.setText(newsData.data.get(position).title);


        menuDetailPager.initData();


    }


}
