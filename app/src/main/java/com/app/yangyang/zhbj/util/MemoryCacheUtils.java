package com.app.yangyang.zhbj.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by yangyang on 2017/6/2.
 */

public class MemoryCacheUtils {

    //没有控制大小可能会出现oom
    /**
     * android 2.3 以上系统会优先将SoftReferenced的资源回收掉
     *
     * LruCache
     * least recent use
     */
//    private Map<String,SoftReference<Bitmap>> bitmapMap = new HashMap<>();
    private LruCache<String,Bitmap> lruCache ;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        this.lruCache = new LruCache<String,Bitmap>((int) maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int sizeByte = value.getByteCount();
                return sizeByte;
            }
        };
    }

    public Bitmap getBitmapFromMemory(String url){
//        SoftReference<Bitmap> softReference_bitmap = bitmapMap.get(url);
//        if(softReference_bitmap!=null){
//            return softReference_bitmap.get();
//        }
        Bitmap bitmap =  lruCache.get(url);
        if (bitmap !=null) {
            return bitmap;
        }
        return null;

    }
    public void setBitmapToMemory(String url,Bitmap bitmap){
//        SoftReference<Bitmap> softReference_bitmap = new SoftReference<Bitmap>(bitmap);
//        bitmapMap.put(url,softReference_bitmap);
        lruCache.put(url,bitmap);
    }



}
