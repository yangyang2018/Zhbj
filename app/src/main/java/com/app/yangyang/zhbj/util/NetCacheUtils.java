package com.app.yangyang.zhbj.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yangyang on 2017/5/30.
 */

public class NetCacheUtils {


    private final LocalCacheUtils localCacheUtils;
    private final MemoryCacheUtils memoryCacheUtils;


    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        imageView.setTag(url);
        new BitmapTask().execute(imageView, url);


    }

    /**
     * 对线程池和handler的封装
     * first fanxing
     * second fanxing
     * third fanxing
     */
    class BitmapTask extends AsyncTask<Object, Object, Bitmap> {


        private ImageView iv_pic;
        private String url;


        //后台耗时操作 --- 子线程
        @Override
        protected Bitmap doInBackground(Object... params) {
            iv_pic = (ImageView) params[0];
            url = (String) params[1];
            return downloadBitmap(url);
        }


        //更新进度  --- 主线程中运行
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        //耗时方法结束后结束该方法 --- 主线程中运行
        @Override
        protected void onPostExecute(Bitmap aVoid) {
            if (aVoid != null) {
                if (iv_pic.getTag().equals(url)) {

                    iv_pic.setImageBitmap(aVoid);
                    System.out.println("从网络缓存中获取图片");
                    localCacheUtils.setBitmapToLocal(url,aVoid);
                    System.out.println("保存图片到本地");
                    memoryCacheUtils.setBitmapToMemory(url,aVoid);
                    System.out.println("保存图片到内存");

                }
            }
        }


        private Bitmap downloadBitmap(String url) {
            HttpURLConnection conn = null;
            Bitmap bitmap = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    InputStream inputStream = conn.getInputStream();
                    //图片压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;//宽高压宿为原来的 1／2
                    options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片的格式


                    bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return bitmap;

        }
    }

}
