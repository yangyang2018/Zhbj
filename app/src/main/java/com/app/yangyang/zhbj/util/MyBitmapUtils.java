package com.app.yangyang.zhbj.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.app.yangyang.zhbj.R;

/**
 * Created by yangyang on 2017/5/30.
 */

public class MyBitmapUtils {


    protected NetCacheUtils netCacheUtils;
    protected LocalCacheUtils localCacheUtils;
    protected MemoryCacheUtils memoryCacheUtils;


    public MyBitmapUtils() {
        this.memoryCacheUtils = new MemoryCacheUtils();
        this.localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
        this.netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);

    }

    public void display(ImageView iv_item_pic, String listimage) {
        //设置默认图片
        iv_item_pic.setImageResource(R.drawable.news_pic_default);
        Bitmap bitmap = null;
        //memory
        bitmap = memoryCacheUtils.getBitmapFromMemory(listimage);
        if (bitmap != null) {
            iv_item_pic.setImageBitmap(bitmap);
            System.out.println("从内存中读取图片资源");
            return;
        }

        //local
        bitmap = localCacheUtils.getBitmapFromLocal(listimage);
        if (bitmap != null) {
            iv_item_pic.setImageBitmap(bitmap);
            System.out.println("从本地读取图片资源");
            return;
        }


        //net
        netCacheUtils.getBitmapFromNet(iv_item_pic, listimage);


    }
}
