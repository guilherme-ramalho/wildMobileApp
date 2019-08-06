package br.com.nanothings.wildmobile.rest;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.nanothings.wildmobile.helper.Constants;
import br.com.nanothings.wildmobile.helper.PreferenceManager;
import br.com.nanothings.wildmobile.model.Cambista;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestRequest {
    private Context context;
    private String token = "", idCambista = "";

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
        final Cambista cambista = (Cambista) new PreferenceManager(context, Cambista.class).getPreference("Cambista");

        if(cambista != null) {
            token = cambista.getSessao().getToken();
            idCambista = Integer.toString(cambista.getId());
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("token", token)
                                .method(originalRequest.method(), originalRequest.body())
                                .build();

                        return chain.proceed(newRequest);
                    }
                });

        return httpClient.build();
    }
}
