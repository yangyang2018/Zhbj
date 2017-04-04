package com.app.yangyang.zhbj.fragment;

import android.view.View;

import com.app.yangyang.zhbj.R;

/**
 * Created by yangyang on 2017/4/4.
 */

public class ContentFragment extends BaseFragment {


    @Override
    public View initView() {
        View  view = View.inflate(mActivity, R.layout.fragment_content_menu,null);
        return view;
    }
}
