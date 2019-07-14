package br.com.nanothings.wildmobile.rest;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.nanothings.wildmobile.helper.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestRequest {
    private Context context;

    public RestRequest(Context context) {
        this.context = context;
    }

    public <S> S getService(Class<S> serviceClass) {
        OkHttpClient httpClient = this.getInterceptor();

        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(
                        GsonConverterFactory.create( new GsonBuilder().serializeNulls().create() )
                )
                .client(httpClient)
                .build()
                .create(serviceClass);
    }

    private OkHttpClient getInterceptor() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("token", "")
                                .method(originalRequest.method(), originalRequest.body())
                                .build();

                        return chain.proceed(newRequest);
                    }
                });

        return httpClient.build();
    }
}
