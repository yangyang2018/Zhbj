package com.app.yangyang.zhbj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.yangyang.zhbj.util.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity {

    private ViewPager vp_guide;
    private Button btn_start;
    private static final int[] mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout ll_group_point;
    private int mPointDistance;
    private View view_red;

    private DisplayMetrics  displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getDisplayMetrics(this);
        initView();
        initUI();

    }


    private void initView() {
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        btn_start = (Button) findViewById(R.id.btn_start);
        ll_group_point = (LinearLayout) findViewById(R.id.ll_group_point);
        view_red = findViewById(R.id.view_red);


        vp_guide.setAdapter(new GuideAdapter());
        vp_guide.setOnPageChangeListener(new GuidePageListener());

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setBoolean(GuideActivity.this,"is_user_guide_showed",true);
                startActivity(new Intent(GuideActivity.this,HomeActivity.class));
                finish();
            }
        });


    }

    private void initUI() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView img = new ImageView(this);
            img.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(img);
        }
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(10*displayMetrics.density),(int)(10*displayMetrics.density));

            if (i > 0) {

                layoutParams.leftMargin = 10;
            }
            point.setLayoutParams(layoutParams);
            ll_group_point.addView(point);
        }


        ll_group_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("layout 结束");
                //防止多次回掉
                ll_group_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDistance = ll_group_point.getChildAt(1).getLeft() - ll_group_point.getChildAt(0).getLeft();
                System.out.println("两个点之间的距离:" + mPointDistance);
            }
        });


    }


    class GuideAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            System.out.println("position" + position + "");
            System.out.println("positionOffset" + positionOffset + "");
            System.out.println("positionOffsetPixels" + positionOffsetPixels + "");

            int len = (int)(mPointDistance * positionOffset+position*mPointDistance);
            RelativeLayout.LayoutParams red_point_layoutParams = (RelativeLayout.LayoutParams) view_red.getLayoutParams();
            red_point_layoutParams.leftMargin = len;
            view_red.setLayoutParams(red_point_layoutParams);



        }

        @Override
        public void onPageSelected(int position) {
            if(position==mImageIds.length-1){
                btn_start.setVisibility(View.VISIBLE);
            }else{
                btn_start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    public static String getDisplayMetrics(Context cx) {
        String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        //取得DisplayMetrics对象方法一
        //dm = cx.getApplicationContext().getResources().getDisplayMetrics();
        //取得DisplayMetrics对象方法二
        ((Activity)cx).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;

        str += "The absolute width:" + String.valueOf(screenWidth) + "pixels\n";
        str += "The absolute heightin:" + String.valueOf(screenHeight) + "pixels\n";
        str += "The logical density of the display.:" + String.valueOf(density)
                + "\n";
        str += "X dimension :" + String.valueOf(xdpi) + "pixels per inch\n";
        str += "Y dimension :" + String.valueOf(ydpi) + "pixels per inch\n";

        System.out.println(str);
        return str;
    }

}
