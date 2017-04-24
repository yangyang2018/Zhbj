package com.app.yangyang.zhbj.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.domain.NewsData;
import com.app.yangyang.zhbj.domain.TabData;
import com.app.yangyang.zhbj.global.GlobalContants;
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
    private ListView lv_list;

    private ArrayList<TabData.TopNewsData> topnews;
    private ArrayList<TabData.TabNewsData> tabnews;
    private NewsAdapter newsAdapter;


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


        return view;
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
                parseData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 解析数据
     *
     * @param result
     */

    private void parseData(String result) {
        Gson gson = new Gson();
        tabDetailData = gson.fromJson(result, TabData.class);

        System.out.println("页签详情页解析结果" + tabDetailData.data.toString());

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
                viewHolder.tv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);
                viewHolder.tv_item_date = (TextView) convertView.findViewById(R.id.tv_item_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            TabData.TabNewsData data = getItem(position);

            viewHolder.tv_item_title.setText(data.title);
            viewHolder.tv_item_date.setText(data.pubdate);

            bitmapUtils.display(viewHolder.iv_item_pc, data.listimage);

            return convertView;
        }
    }

    static class ViewHolder {

        private ImageView iv_item_pc;
        private TextView tv_item_title;
        private TextView tv_item_date;


    }
}
