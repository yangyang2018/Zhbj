package com.app.yangyang.zhbj.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.app.yangyang.zhbj.R;

/**
 * Created by yangyang on 2017/4/4.
 */

public class ContentFragment extends BaseFragment {


    private RadioGroup rg_group;

    @Override
    public View initView() {
        View  view = View.inflate(mActivity, R.layout.fragment_content_menu,null);

        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        rg_group.check(R.id.rb_home);
    }
}
