package com.app.yangyang.zhbj.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yangyang.zhbj.NewsDetailActivity;
import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.domain.NewsData;
import com.app.yangyang.zhbj.domain.TabData;
import com.app.yangyang.zhbj.global.GlobalContants;
import com.app.yangyang.zhbj.util.PrefUtils;
import com.app.yangyang.zhbj.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import static com.app.yangyang.zhbj.R.id.tv_item_title;

/**
 * Created by yangyang on 2017/4/6.
 */

public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {

    public NewsData.NewsTypeData tabData;
//    private TextView textView;

    public String mUrl;
    private TabData tabDetailData;

    @ViewInject(R.id.vp_news)
    private ViewPager mViewPager;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.cpi_indicator)
    private CirclePageIndicator cpi_indicator;


    @ViewInject(R.id.lv_list)
    private RefreshListView lv_list;

    private ArrayList<TabData.TopNewsData> topnews;
    private ArrayList<TabData.TabNewsData> tabnews;
    private NewsAdapter newsAdapter;
    private String myMoreUrl;


    public TabDetailPager(Activity activity, NewsData.NewsTypeData data) {
        super(activity);
        this.tabData = data;
        this.mUrl = GlobalContants.SERVER_URL + data.url;
    }

    @Override
    public View initView() {

//        textView = new TextView(mActivity);
//        textView.setText("页签详情页");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        textView.setGravity(Gravity.CENTER);
//        return textView;
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        ViewUtils.inject(this, view);
        ViewUtils.inject(this, headerView);

        lv_list.addHeaderView(headerView);


        lv_list.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadmore() {
                Log.d("onLoadmore",myMoreUrl+"");
                //加载下一页
                if (myMoreUrl!=null) {
                    getMoreDataFromServer();
                }else{
                    System.out.println("我是有底线的哦" +
                            "2017");
                    Toast.makeText(mActivity,"我是有底线的",Toast.LENGTH_LONG);
                    lv_list.onRefreshComplete(false);
                }

            }
        });

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("dian ji le"+position);

                String ids = PrefUtils.getString(mActivity,"read_ids","");
                String targetId = tabnews.get(position).id;
                if(!ids.contains(targetId)){

                    System.out.println("targetId"+targetId);
                    ids = ids + targetId+",";


                    PrefUtils.setString(mActivity,"read_ids",ids);
                }
//                newsAdapter.notifyDataSetChanged();

                changeTitleColor(view);

                Bundle  bundle =  new Bundle();
                bundle.putString("url",tabnews.get(position).url);
                System.out.println("url"+tabnews.get(position).url);
                Intent  intent  =  new Intent();
                intent.putExtras(bundle);
                intent.setClass(mActivity, NewsDetailActivity.class);

                mActivity.startActivity(intent);


            }
        });


        return view;
    }

    /**
     * 改变一点击item的颜色
     * @param view
     */
    private void changeTitleColor(View view) {
        TextView  tvTitle = (TextView) view.findViewById(R.id.tv_item_title);
        tvTitle.setTextColor(Color.GRAY);

    }


    @Override
    public void initData() {

//        tv_.setText(tabData.title);

        getDataFromServer();

    }


    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {


            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("页签详情页数据" + result);
                parseData(result,false);
                lv_list.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {

                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT);
                e.printStackTrace();
                lv_list.onRefreshComplete(false);

            }
        });
    }

    /**
     * getmorefrom server
     */
    public void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, myMoreUrl, new RequestCallBack<String>() {


            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("页签详情页数据" + result);
                parseData(result,true);
                lv_list.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {

                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT);
                e.printStackTrace();
                lv_list.onRefreshComplete(false);

            }
        });
    }

    /**
     * 解析数据
     *
     * @param result
     */

    private void parseData(String result,boolean isLoadmore) {
        Gson gson = new Gson();
        tabDetailData = gson.fromJson(result, TabData.class);

        System.out.println("页签详情页解析结果" + tabDetailData.data.toString());
        String more = tabDetailData.data.more;
        if (!TextUtils.isEmpty(more)) {
            myMoreUrl =GlobalContants.SERVER_URL+tabDetailData.data.more;
        }else {
            myMoreUrl = null;
        }


        if (!isLoadmore) {

            topnews = tabDetailData.data.topnews;

            tabnews = tabDetailData.data.news;

            if (topnews != null) {

                mViewPager.setAdapter(new TopNewsAdapter());
                cpi_indicator.setViewPager(mViewPager);
                //支持快照
                cpi_indicator.setSnap(true);
                cpi_indicator.setOnPageChangeListener(this);
                cpi_indicator.onPageSelected(0);

                tv_title.setText(topnews.get(0).title);


            }
            if (tabnews != null) {
                newsAdapter = new NewsAdapter();
//        //填充新闻页数据
                lv_list.setAdapter(newsAdapter);

            }


        }else {
            ArrayList<TabData.TabNewsData> morenews = tabDetailData.data.news;
            tabnews.addAll(morenews);
            newsAdapter.notifyDataSetChanged();

        }







    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_title.setText(topnews.get(position).title);


    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    class TopNewsAdapter extends PagerAdapter {

        private BitmapUtils bitmapUtils;

        public TopNewsAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);

            bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);
        }


        @Override
        public int getCount() {
            return tabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
//            imageView.setBackgroundResource(R.drawable.topnews_item_default);

//            imageView.setImageResource(R.drawable.topnews_item_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            TabData.TopNewsData topNewsData = topnews.get(position);
            bitmapUtils.display(imageView, topNewsData.topimage);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter {

        private BitmapUtils bitmapUtils;

        public NewsAdapter() {

            bitmapUtils = new BitmapUtils(mActivity);

            bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }


        @Override
        public int getCount() {
            return tabnews.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return tabnews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_tabnews, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_item_pc = (ImageView) convertView.findViewById(R.id.iv_item_pc);
                viewHolder.tv_item_title = (TextView) convertView.findViewById(tv_item_title);
                viewHolder.tv_item_date = (TextView) convertView.findViewById(R.id.tv_item_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            TabData.TabNewsData data = getItem(position);

            viewHolder.tv_item_title.setText(data.title);
            viewHolder.tv_item_date.setText(data.pubdate);

            bitmapUtils.display(viewHolder.iv_item_pc, data.listimage);

            String ids = PrefUtils.getString(mActivity,"read_ids","");
            System.out.println("从sp读取："+ids);
            if (ids.contains(getItem(position).id+"")) {

                viewHolder.tv_item_title.setTextColor(Color.GRAY);
            }else{
                viewHolder.tv_item_title.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }

    static class ViewHolder {

        private ImageView iv_item_pc;
        private TextView tv_item_title;
        private TextView tv_item_date;


    }
}
