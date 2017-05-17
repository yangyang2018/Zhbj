package com.app.yangyang.zhbj.domain;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/4/9.
 */

public class TabData {
    public  int retcode;

    public TabDetail data;

    public class  TabDetail  {


        public  String title;
        public  String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetail{" +
                    "title='" + title + '\'' +
                    ", more='" + more + '\'' +
                    ", news=" + news +
                    ", topnews=" + topnews +
                    '}';
        }
    }

    public class  TabNewsData  {

        public  boolean comment;
        public  String commentlist;
        public  String commenturl;
        public  String id;
        public  String pubdate;
        public  String title;
        public  String listimage;
        public  String type;
        public  String url;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    public class  TopNewsData  {

        public  boolean comment;
        public  String commentlist;
        public  String commenturl;
        public  int id;
        public  String pubdate;
        public  String title;
        public  String topimage;
        public  String type;
        public  String url;

        @Override
        public String toString() {
            return "TopNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }


}
