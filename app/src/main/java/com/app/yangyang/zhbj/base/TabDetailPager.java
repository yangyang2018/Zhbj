package com.app.yangyang.zhbj.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

/**
 * Created by yangyang on 2017/4/6.
 */

public class TabDetailPager extends BaseMenuDetailPager {

    public NewsData.NewsTypeData tabData;
//    private TextView textView;

    public String mUrl;
    private TabData tabDetailData;

    @ViewInject(R.id.vp_news)
    private ViewPager  mViewPager;

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

        ViewUtils.inject(this,view);
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

        System.out.println("页签详情页解析结果"+tabDetailData.data.toString());

        mViewPager.setAdapter(new TopNewsAdapter());

    }

    class  TopNewsAdapter extends PagerAdapter{

        private BitmapUtils bitmapUtils;

        public TopNewsAdapter(){
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

            imageView.setImageResource(R.drawable.topnews_item_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            TabData.TopNewsData topNewsData = tabDetailData.data.topnews.get(position);
            bitmapUtils.display(imageView,topNewsData.topimage);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }
}
