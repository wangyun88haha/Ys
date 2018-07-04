package com.ys.modulecommon.clound;


import com.ys.modulecommon.utils.CommonUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/6/8.
 */
public class OkhttpClientManager {
    private static final int DEFAULT_TIME_OUT = 10;//超时时间 10秒
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;
    private OkHttpClient okHttpClient;

    private OkhttpClientManager() {
         okHttpClient = new OkHttpClient.Builder().
                connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS).
                readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS).
                writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS).retryOnConnectionFailure(false).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded; text/html; charset=utf-8")
                        .header("Accept", "text/html")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    private static OkhttpClientManager okhttpClientManager;

    public static OkhttpClientManager getSingleInstance() {
        if (okhttpClientManager == null) {
            synchronized (OkhttpClientManager.class) {
                if (okhttpClientManager == null) {
                    okhttpClientManager = new OkhttpClientManager();
                }
            }
        }
        return okhttpClientManager;
    }

    /**
     * 同步提交数据
     * @param url
     * @param postMap
     * @return
     * @throws IOException
     */
    public String postData(String url, Map<String,String> postMap) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(CommonUtil.mapToBuilder(postMap).build())
                .build();
        Call call = okHttpClient.newCall(request);
        String result = call.execute().body().string();
        return result;
    }

    /**
     * 取消所有请求
     */
    public void cancelAllRequest() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }
}
