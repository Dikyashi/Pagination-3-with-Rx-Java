package com.example.pagination3;

import com.example.pagination3.Model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    static APIInterface apiInterface;
    private static final String TAG = "api_key";
    private static final String API_KEY ="fb976f8b90a72dc0a7c1772c0ae59401";

    public static APIInterface getApiInterface(){

        if(apiInterface==null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(interceptor);

            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalUrl = original.url();
                HttpUrl modifiedUrl = originalUrl.newBuilder()
                        .addQueryParameter(TAG, API_KEY).build();
                Request.Builder requestBuilder = original.newBuilder().url(modifiedUrl);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    public interface APIInterface{
        @GET("movie/popular")
        Single<MovieResponse> getMoviesByPage(@Query("page") int page);
    }
}
