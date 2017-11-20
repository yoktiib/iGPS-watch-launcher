package com.pomohouse.library.networks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pomohouse.library.WearerInfoUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Sirawit on 11/16/2014.
 */
public class ServiceApiGenerator extends BaseServiceGenerator {

    private static ServiceApiGenerator instance = null;
    //private String API_BASE_URL = "http://203.151.232.222:3000/v1.1/api/watch/";
    //private String API_BASE_URL = "http://203.151.232.222:3000/v1.2/api/watch/";
    //private String API_BASE_URL = "http://api.pomowaffle.com/v1.1/api/watch/";
    //private String API_BASE_URL = "http://api.pomowaffle.com/v1.2/api/watch/";
    //private String API_BASE_URL = "http://45.79.203.221:3000/v1.2/api/watch/";
    //private String API_BASE_URL = "http://54.169.222.17:3000/v1.2/api/watch/";
    private String API_BASE_URL = "http://api.igps-server.com/v1.2/api/watch/";

    private Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL);

    public static ServiceApiGenerator getInstance() {
        if (instance == null)
            instance = new ServiceApiGenerator();
        return instance;
    }

    public static ServiceApiGenerator getInstance(String baseURL) {
        if (instance == null)
            instance = new ServiceApiGenerator();
        instance.API_BASE_URL = baseURL;
        return instance;
    }

    private ServiceApiGenerator() {
    }

    public <TService> TService createService(Class<TService> serviceClass) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = builder.baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpBuilder().build()).build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient.Builder httpBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("language", WearerInfoUtils.getInstance().getLanguage())
                    .addHeader("platform", WearerInfoUtils.getInstance().getPlatform())
                    .addHeader("version", WearerInfoUtils.getInstance().getPomoVersion())
                    .addHeader("imei", WearerInfoUtils.getInstance().getImei())
                    .method(original.method(), original.body()).build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(interceptor);
        return httpClient;
    }
}