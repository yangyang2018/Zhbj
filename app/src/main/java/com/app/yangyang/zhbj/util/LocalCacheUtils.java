package com.app.yangyang.zhbj.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by yangyang on 2017/6/2.
 */

public class LocalCacheUtils {

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhbj_cache_52";
    private final MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
        this.memoryCacheUtils = memoryCacheUtils;
    }


    public Bitmap getBitmapFromLocal(String url){
        Bitmap  bitmap = null;
                String filename = Md5Encrypt.md5(url);
        File file = new File(CACHE_PATH,filename);
        if(file.exists()){
            try {
                bitmap =  BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(bitmap!=null){
            memoryCacheUtils.setBitmapToMemory(url,bitmap);
            System.out.println("保存图片到内存中啦");
        }
        return bitmap;

    }

    public void setBitmapToLocal(String url, Bitmap bitmap){
        String filename = Md5Encrypt.md5(url);
        File file = new File(CACHE_PATH,filename);
        File parentFile = file.getParentFile();
        System.out.println("文件夹是否存在"+parentFile.exists());
        if (!parentFile.exists()) {
            boolean b = parentFile.mkdirs();
            System.out.println("创建文件夹是否成功"+b);
        }
        //本地压缩
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
