package com.app.yangyang.zhbj.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.yangyang.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangyang on 2017/4/23.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener ,AdapterView.OnItemClickListener{

    private int mMeasureHeight;
    private int mFooterMeasureHeight;


    private static final int STATE_PULL_REFRESH = 0;
    private static final int STATE_RELEASE_REFRESH = 1;
    private static final int STATE_REFRESHING = 2;

    private int current_state = STATE_PULL_REFRESH;


    private int startY = -1;
    private View mHeaderView;
    private View mFooterView;
    private TextView tv_date;
    private TextView tv_title;
    private ImageView iv_arrow;
    private ProgressBar pb_refresh_head;
    private RotateAnimation animation_up;
    private RotateAnimation animation_down;



    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initArowAnim();
        initFootView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initArowAnim();
        initFootView();

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initArowAnim();
        initFootView();

    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);

        this.addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0, 0);
        mMeasureHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mMeasureHeight, 0, 0);


        tv_title = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tv_date = (TextView) mHeaderView.findViewById(R.id.tv_date);
        iv_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
        pb_refresh_head = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh_head);

        //默认刷新时间
        tv_date.setText("最后刷新时间：" + getCurrentTime());
    }

    /**
     * init foot view
     */
     private void  initFootView(){

         mFooterView = View.inflate(getContext(), R.layout.refresh_footer, null);


         this.addFooterView(mFooterView);

         //init hide  footerview隐藏布局
         mFooterView.measure(0, 0);
         mFooterMeasureHeight = mFooterView.getMeasuredHeight();
         mFooterView.setPadding(0, -mFooterMeasureHeight, 0, 0);

         this.setOnScrollListener(this);




     }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //避免有时action down 没有戳发 startY 没有值
                if (startY == -1) {
                    startY = (int) ev.getRawY();
                }
                if (current_state == STATE_REFRESHING) {
                    break;
                }
                int endY = (int) ev.getRawY();
                //移动偏移量
                int dy = endY - startY;

                if (dy > 0 && getFirstVisiblePosition() == 0) {


                    int padding = dy - mMeasureHeight;

                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && current_state != STATE_RELEASE_REFRESH) {
                        current_state = STATE_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && current_state != STATE_PULL_REFRESH) {
                        current_state = STATE_PULL_REFRESH;
                        refreshState();
                    }
                    return true;

                }


                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//  重制

                if (current_state == STATE_RELEASE_REFRESH) {

                    current_state = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);

                    refreshState();
                } else if (current_state == STATE_PULL_REFRESH) {

                    mHeaderView.setPadding(0, -mMeasureHeight, 0, 0);
                    refreshState();
                }


                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);


    }

    private void refreshState() {

        switch (current_state) {
            case STATE_PULL_REFRESH:
                tv_title.setText("下拉刷新");
                iv_arrow.setVisibility(VISIBLE);
                pb_refresh_head.setVisibility(INVISIBLE);

                iv_arrow.startAnimation(animation_down);

                break;
            case STATE_RELEASE_REFRESH:
                tv_title.setText("松开刷新");
                iv_arrow.setVisibility(VISIBLE);
                pb_refresh_head.setVisibility(INVISIBLE);
                iv_arrow.startAnimation(animation_up);

                break;
            case STATE_REFRESHING:
                tv_title.setText("正在刷新");
                iv_arrow.clearAnimation();//必须先清除动画才能隐藏
                iv_arrow.setVisibility(INVISIBLE);
                pb_refresh_head.setVisibility(VISIBLE);
                //shuxing
                if (onRefreshListener != null) {

                    onRefreshListener.onRefresh();
                }
                break;


        }


    }

    private void initArowAnim() {
        //尖头向上动画
        animation_up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);

        animation_up.setDuration(500);
        animation_up.setFillAfter(true);

        //尖头向xia动画
        animation_down = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);
        animation_down.setDuration(500);
        animation_down.setFillAfter(true);

    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {

        this.onRefreshListener = onRefreshListener;
    }


    private boolean  loadingMore =false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState"+scrollState);

        if(scrollState == SCROLL_STATE_IDLE
                || scrollState == SCROLL_STATE_FLING){

            if (getLastVisiblePosition() == getCount()-1 && !loadingMore) {
                System.out.println("到底了。。。。");

                mFooterView.setPadding(0,0,0,0);

                setSelection(getCount()-1);
                loadingMore = true;

                if(onRefreshListener!=null){

                    onRefreshListener.onLoadmore();
                }
            }


        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(myOnItemClickListener != null){
            myOnItemClickListener.onItemClick(parent,view,position-2,id);

        }


    }

    public interface OnRefreshListener {

        public void onRefresh();

        //loadmore
        public void onLoadmore();

    }

    //下拉刷新完成收起
    public void onRefreshComplete(boolean success) {
        if (loadingMore) {

            mFooterView.setPadding(0,-mFooterMeasureHeight,0,0);
            loadingMore = false;

        }else{

            current_state = STATE_PULL_REFRESH;
            tv_title.setText("下拉刷新");
            iv_arrow.setVisibility(VISIBLE);
            pb_refresh_head.setVisibility(INVISIBLE);
            mHeaderView.setPadding(0, -mMeasureHeight, 0, 0);
            if (success) {

                tv_date.setText("最后刷新时间：" + getCurrentTime());
            }

        }
    }


    public String getCurrentTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());


    }

    OnItemClickListener  myOnItemClickListener;

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        myOnItemClickListener = listener;
    }
}



