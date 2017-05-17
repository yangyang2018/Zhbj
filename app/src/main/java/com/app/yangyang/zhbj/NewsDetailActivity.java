package com.app.yangyang.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends Activity implements View.OnClickListener {


    private WebView wv_web;
    private ImageButton btn_size;
    private ImageButton btn_share;
    private ImageButton btn_back;
    private ProgressBar pb_over_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_share = (ImageButton) findViewById(R.id.btn_share);
        btn_size = (ImageButton) findViewById(R.id.btn_size);

        pb_over_webview = (ProgressBar) findViewById(R.id.pb_over_webview);

        btn_back.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_size.setOnClickListener(this);

        wv_web = (WebView) findViewById(R.id.wv_web);

        String url = getIntent().getExtras().getString("url");


        WebSettings settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);//显示放大缩小按钮
        settings.setUseWideViewPort(true);//双击放大


        wv_web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_over_webview.setVisibility(View.VISIBLE);
                System.out.println("网页加载开始");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                pb_over_webview.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                System.out.println("加载的目标URL："+url);
                wv_web.loadUrl(url);
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        wv_web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                System.out.println("网页加载进度："+newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("网页加载标题："+title);
                super.onReceivedTitle(view, title);
            }
        });

//        wv_web.loadUrl("http://www.iqiyi.com/");

        wv_web.loadUrl(url);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_back:
                finish();
                break;

            case R.id.btn_share:

                showShare();


                break;

            case R.id.btn_size:
                showChoseDialog();
                break;


        }



    }

    private  int mCurrentSelectedItem;

    private int  mCurrentItem = 1;

    private void showChoseDialog() {
        AlertDialog.Builder  builder =  new AlertDialog.Builder(this);
        String[]  items = new String[]{"大号字体","正常字体","小号字体"};
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("selected:"+which);
                mCurrentSelectedItem = which;

            }
        });

        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings  settings = wv_web.getSettings();
                switch (which){
                    case 0:
//                        settings.setTextZoom(50);
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 1:
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
//                        settings.setTextZoom(30);
                        break;
                    case 2:
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
//                        settings.setTextZoom(10);
                        break;

                }
                mCurrentItem = mCurrentSelectedItem;
            }
        });

        builder.setNegativeButton("cancel",null);

        builder.show();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("新浪微博");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
