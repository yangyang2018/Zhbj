package com.app.yangyang.zhbj.domain;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/8.
 */

public class NewsData {

    public int retcode;
    public ArrayList<NewsMenuData> data;

    /**
     * 侧边栏数据对象
     */
    public class NewsMenuData {
        public String id;
        public String title;
        public int type;
        public String url;


        public ArrayList<NewsTypeData> children;


        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }


    /**
     * 类型数据对象
     */
    public class NewsTypeData {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTypeData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "NewsData{" +
                "data=" + data +
                '}';
    }
}
