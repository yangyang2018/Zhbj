package com.app.yangyang.zhbj.util;

import android.content.Context;

/**
 * Created by yangyang on 2017/5/18.
 */

public class CacheUtils {

    public static void setCache(String key, String value, Context context) {

        PrefUtils.setString(context, key, value);

    }

    public static String getCache(String key, Context context) {

        return PrefUtils.getString(context, key, null);

    }


}
