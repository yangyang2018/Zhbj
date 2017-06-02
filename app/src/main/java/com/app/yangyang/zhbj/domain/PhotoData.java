package com.app.yangyang.zhbj.domain;

import java.util.ArrayList;

/**
 * Created by yangyang on 2017/5/23.
 */

public class PhotoData {

    public  int  retcode;

    public PhotosInfo data;

    public  class  PhotosInfo{

        public String title;

        public ArrayList<PhotoInfo> news;

    }

    public  class  PhotoInfo{

        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

    }

}
