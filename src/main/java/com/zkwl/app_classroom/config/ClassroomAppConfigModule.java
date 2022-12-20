package com.zkwl.app_classroom.config;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.RoomDatabase;

import com.google.gson.GsonBuilder;
import com.king.frame.mvvmframe.config.FrameConfigModule;
import com.king.frame.mvvmframe.di.module.ConfigModule;
import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.zkwl.app_classroom.BuildConfig;
import com.zkwl.app_classroom.api.ClassroomConstants;
import com.zkwl.app_classroom.util.SpsUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


/**
 * 自定义全局配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class ClassroomAppConfigModule extends FrameConfigModule {
    @Override
    public void applyOptions(Context context, ConfigModule.Builder builder) {
        /**
         * > 目前通过设置 BaseUrl 的入口主要有两种：
         * >> 1.一种是通过在 Manifest 中配置 meta-data 的来自定义 FrameConfigModule,在里面 通过 {@link ConfigModule.Builder#baseUrl(String)}来配置 BaseUrl。（一次设置，全局配置）
         * >
         * >> 2.一种就是通过RetrofitHelper {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来配置 BaseUrl。（可多次设置，动态全局配置，有前提条件）
         * >
         * > 以上两种配置 BaseUrl 的方式都可以达到目的。但是你可以根据不同的场景选择不同的配置方式。
         * >
         * > 主要场景与选择如下：
         * >
         * >> 一般场景：对于只使用单个不变的 BaseUrl的
         * >>>     场景1:如果本库的默认已满足你的需求，无需额外自定义配置的。
         * >          选择：建议你直接使用 {@link RetrofitHelper#setBaseUrl(String)} 或 {@link RetrofitHelper#setBaseUrl(HttpUrl)} 来初始化 BaseUrl，切记在框架配置初始化 BaseUrl之前，建议在你自定义的 {@link Application#onCreate()}中初始化。
         * >
         * >>>     场景2:如果本库的默认配置不满足你的需求，你需要自定义一些配置的。（比如需要使用 RxJava相关）
         * >          选择：建议你在自定义配置中通过 {@link ConfigModule.Builder#baseUrl(String)} 来初始化 BaseUrl。
         * >
         * >> 二般场景：对于只使用单个 BaseUrl 但是，BaseUrl中途会变动的。
         * >>>     场景3：和一般场景一样，也能分两种，所以选择也和一般场景也可以是一样的。
         * >          选择：两种选择都行，但当 BaseUrl需要中途变动时，还需将 {@link RetrofitHelper#setDynamicDomain(boolean)} 设置为 {@code true} 才能支持动态改变 BaseUrl。
         * >
         * >> 特殊场景：对于支持多个 BaseUrl 且支持动态可变的。
         * >>>        选择：这个场景的选择，主要涉及到另外的方法，请查看 {@link RetrofitHelper#putDomain(String, String)} 和 {@link RetrofitHelper#putDomain(String, HttpUrl)}相关详情
         * >
         */
        //通过第一种方式初始化BaseUrl
        builder.baseUrl(ClassroomConstants.BASE_URL);//TODO 配置Retrofit中的baseUrl
        builder.retrofitOptions(new RetrofitOptions() {
                    @Override
                    public void applyOptions(Retrofit.Builder builder) {
                        //TODO 配置Retrofit
                        //如想使用RxJava
                        //builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    }
                })
                .okHttpClientOptions(new OkHttpClientOptions() {
                    @Override
                    public void applyOptions(OkHttpClient.Builder builder) {
                        //TODO 配置OkHttpClient
                        builder.addInterceptor(new Interceptor() {
                            @NotNull
                            @Override
                            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();
                                Request.Builder requestBuilder = request.newBuilder();
                                HttpUrl.Builder urlBuilder = request.url().newBuilder();
                                Log.d("DEBUG", "-------------------");
                                if ("GET".equals(request.method())) { // GET方法
                                    // 这里可以添加一些公共get参数
                                    HttpUrl httpUrl = urlBuilder.build();

                                    // 打印所有get参数
                                    Set<String> paramKeys = httpUrl.queryParameterNames();
                                    for (String key : paramKeys) {
                                        String value = httpUrl.queryParameter(key);
                                        Log.d("DEBUG", key + ":" + value);
                                    }
                                    // 将最终的url填充到request中
                                    requestBuilder.url(httpUrl);
                                } else if ("POST".equals(request.method())) { // POST方法
                                    // FormBody和url不太一样，若需添加公共参数，需要新建一个构造器
                                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                                    // 把已有的post参数添加到新的构造器
                                    if (request.body() instanceof FormBody) {
                                        FormBody formBody = (FormBody) request.body();
                                        for (int i = 0; i < formBody.size(); i++) {
                                            bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                                        }
                                    }
                                    // 这里可以添加一些公共post参数
                                    FormBody newBody = bodyBuilder.build();
                                    // 打印所有post参数
                                    for (int i = 0; i < newBody.size(); i++) {
                                        Log.d("DEBUG", newBody.name(i) + ":" + newBody.value(i));
                                    }

                                    // 将最终的表单body填充到request中
                                    requestBuilder.post(newBody);
                                }

                                String access_token = SpsUtil.getString(context, "access_token", "");
                                if (BuildConfig.DEBUG){
                                    //单独运行模块时的临时token
//                                    access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJhdWQiOiIiLCJpYXQiOjE2NjkxMDU0MTAsImV4cCI6MTY3MTY5NzQxMCwibmJmIjoxMzU3MDAwMDAwLCJqdGkiOiI0OTg2RUFBRDIwRUI4NjU2MkYxM0VFNUZENDQyRUM1QyIsImlkIjoxNDgyLCJuaWNrbmFtZSI6Ilx1NTQ2OFx1OTFkMVx1NjY1MyJ9.Lb-ll10JzBjOWAVinVB3IepsqjU4o-7cssmdsa_uJCw";
                                }
                                requestBuilder.addHeader("Authorization", ""+access_token);
                                Log.d("DEBUG", "-------------------");
                                return chain.proceed(requestBuilder.build());
                            }
                        });
                    }
                })
                .gsonOptions(new GsonOptions() {
                    @Override
                    public void applyOptions(GsonBuilder builder) {
                        //TODO 配置Gson
                    }
                })
                .roomDatabaseOptions(new RoomDatabaseOptions<RoomDatabase>() {
                    @Override
                    public void applyOptions(RoomDatabase.Builder<RoomDatabase> builder) {
                        //TODO 配置RoomDatabase
                    }
                });
    }
}
