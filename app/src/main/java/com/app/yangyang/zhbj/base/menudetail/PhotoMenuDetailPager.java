package com.app.yangyang.zhbj.base.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.base.BaseMenuDetailPager;
import com.app.yangyang.zhbj.domain.PhotoData;
import com.app.yangyang.zhbj.global.GlobalContants;
import com.app.yangyang.zhbj.util.CacheUtils;
import com.app.yangyang.zhbj.util.MyBitmapUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
/**
 * @ author yangyang
 * @ time
 * @ email shayu2017@163.com
 * @ version 1.0
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private GridView gv_photo;
    private ListView lv_photo;
    private ArrayList<PhotoData.PhotoInfo> photos;
    private PhotoAdapter mPhotoAdapter;
    private ImageButton btn_photo;

    private boolean isListDisplay = true;



    public PhotoMenuDetailPager(Activity activity, ImageButton btn_photo) {
        super(activity);
        this.btn_photo = btn_photo;
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();

            }
        });
    }

    private void changeDisplay() {
        if (isListDisplay) {
            isListDisplay = false;
            lv_photo.setVisibility(View.GONE);
            gv_photo.setVisibility(View.VISIBLE);
            btn_photo.setImageResource(R.drawable.icon_pic_grid_type);
        }else{
            isListDisplay = true;
            lv_photo.setVisibility(View.VISIBLE);
            gv_photo.setVisibility(View.GONE);
            btn_photo.setImageResource(R.drawable.icon_pic_list_type);
        }

    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);

        lv_photo = (ListView) view.findViewById(R.id.lv_photo);
        gv_photo = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    @Override
    public void initData() {

        String cache = CacheUtils.getCache(GlobalContants.PHOTOE_URL, mActivity);

        if (TextUtils.isEmpty(cache)) {

            getDataFromServer();

        }
        parseData(cache);


    }

    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalContants.PHOTOE_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("返回结果：" + result);
                parseData(result);
                //put data into  cache
                CacheUtils.setCache(GlobalContants.PHOTOE_URL, result, mActivity);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

    private void parseData(String result) {

        Gson gson = new Gson();

        PhotoData data = gson.fromJson(result, PhotoData.class);

        photos = data.data.news;

        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter();
            lv_photo.setAdapter(mPhotoAdapter);
            gv_photo.setAdapter(mPhotoAdapter);

        }


    }


    class PhotoAdapter extends BaseAdapter {

//        private BitmapUtils bitmapUtils;
        private MyBitmapUtils myBitmapUtils;

        public PhotoAdapter() {
//            this.bitmapUtils = new BitmapUtils(mActivity);
//            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
            myBitmapUtils = new MyBitmapUtils();
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public PhotoData.PhotoInfo getItem(int position) {
            return photos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {

                convertView = View.inflate(mActivity, R.layout.list_photo_item, null);

                viewHolder = new ViewHolder();

                viewHolder.iv_item_pic = (ImageView) convertView.findViewById(R.id.iv_item_pic);
                viewHolder.tv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            PhotoData.PhotoInfo cell = getItem(position);


            viewHolder.tv_item_title.setText(cell.title);

//            bitmapUtils.display(viewHolder.iv_item_pic,cell.listimage);

            myBitmapUtils.display(viewHolder.iv_item_pic,cell.listimage);
            return convertView;
        }
    }

    static class ViewHolder {

        public ImageView iv_item_pic;
        public TextView tv_item_title;

    }
}
