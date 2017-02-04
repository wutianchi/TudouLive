package com.bentudou.tudoulive.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.bentudou.tudoulive.util.SharePreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lzz on 2016/9/19.
 * 简化后的 Retrofit HTTP 客户端调用类.
 */
public class RTHttpClient {
    public static Retrofit retrofit = null;
    public static String access_token;
    public static String baseURL = null;
    public static void init(String _baseURL,Context context) {
        access_token=SharePreferencesUtils.getBtdToken(context);
        baseURL=_baseURL;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (access_token.equals("")) {
                    return chain.proceed(originalRequest);
                }
                Request authorised = originalRequest.newBuilder()
                        .header("access-token",access_token)
                        .build();
                return chain.proceed(authorised);
            }
        };
        // OkHttp3.0的使用方式
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(mTokenInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public static <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
