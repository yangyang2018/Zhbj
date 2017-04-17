package com.app.yangyang.zhbj.testOkHttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.yangyang.zhbj.R;
import com.app.yangyang.zhbj.common.UrlValue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestOkHttpActivity extends Activity {

    private static final  String  TAG = "TestOkHttpActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ok_http);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("button","click");
//                testGet();
//                testPost();
//                textRxJava();
                testGetTranslates();
            }

        });


    }

    private void testGetTranslates() {


//        OkhttpUtils.getInstance().doPost(TestOkHttpActivity.this,UrlValue.JK1002,null,false,new OkhttpUtils.MyCallback(){
//            @Override
//            public void onSuccess(String result) {
//                Log.d("onSuccess",result);
//            }
//
//            @Override
//            public void onFailture(Exception e) {
//                Log.d("onFailture",""+e);
//            }
//
//            @Override
//            public void onStart() {
//                Log.d("onStart","你是猪吗?");
//            }
//
//            @Override
//            public void onFinish() {
//                Log.d("onFinish","你是猪吗?");
//            }
//        });

    }

    private void textRxJava() {
        final OkHttpClient okHttpClient = new OkHttpClient();

        //
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(UrlValue.JK1001)
                .build();
        rx.Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    //处理请求在IO线程中进行
                    final Response response = okHttpClient.newCall(request).execute();
                    subscriber.onNext(response.body().string());
                }
                catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d("onStart","你是猪吗?");
                    }
                    @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable e) {
                    Log.d("onError",e.toString());
                }
                @Override
                public void onNext(String s) {
                    //处理返回结果，在UI线程中，可以直接显示结果
                    Log.d("onNext",s);
                }
            }) ;


    }

    private void testPost() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("key", "value")
                .build();
        String params = getParameters(requestBody);
        Log.d("params",params);
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(UrlValue.JK1001)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "testHttpPost ... onFailure() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "testHttpPost ... onResponse() response=" + response.body().string());
            }
        });


    }

    private void testGet() {
        OkHttpClient okHttpClient = new OkHttpClient();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(UrlValue.JK1001)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure() e=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse() result=" + response.toString());
                Log.e(TAG, "onResponse() result=" + response.body().string());
            }
        });



    }

    public String getParameters(RequestBody requestBody) {
        try {
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            //
            BufferedInputStream bufferedInputStream = new BufferedInputStream(buffer.inputStream());
            final StringBuffer resultBuffer = new StringBuffer();
            byte[] inputBytes = new byte[1024];
            while (true) {
                int count = bufferedInputStream.read(inputBytes);
                if (count <= 0) {
                    break;
                }
                resultBuffer.append(new String(Arrays.copyOf(inputBytes, count), Util.UTF_8));
            }
            final String parameter = URLDecoder.decode(resultBuffer.toString(),
                    Util.UTF_8.name());
            bufferedInputStream.close();


            return parameter;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    




}
