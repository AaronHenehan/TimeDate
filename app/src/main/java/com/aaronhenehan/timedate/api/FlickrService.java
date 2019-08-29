package com.aaronhenehan.timedate.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aaronhenehan.timedate.model.Photo;
import com.aaronhenehan.timedate.model.PhotoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrService {
    private static final String TAG = "FlickrService";

    private static final String API_KEY = "9f6e4500c1cdfd30090463e6f0713ebd";
    private static final String BASE_URL = "https://www.flickr.com/services/";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final String FORMAT = "json";

    private Retrofit retrofit;
    private OkHttpClient client;
    private FlickrApi flickrApi;
    private MutableLiveData<String> apiError;
    private MutableLiveData<List<Photo>> photoLiveData = new MutableLiveData<>();

    public FlickrService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(new FlickrRequestInterceptor())
                .addInterceptor(logging)
                .build();

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        flickrApi = retrofit.create(FlickrApi.class);
        apiError = new MutableLiveData<>();
    }

    public void getPhotosByTags(String tags) {
        Call<PhotoResponse> call = flickrApi.searchPhotosByTag(SEARCH_METHOD, API_KEY, tags, FORMAT, 1);
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, retrofit2.Response<PhotoResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Code: " + response.code());
                    return;
                }

                photoLiveData.setValue(response.body().getFlickrPhotoObject().getPhotos());
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                apiError.setValue(t.getMessage());
            }
        });
    }

    public void getPhotosByUploadDate(long minUploadDate, long maxUploadDate) {
        Call<PhotoResponse> call = flickrApi.searchPhotosByUploadDate(SEARCH_METHOD, API_KEY, minUploadDate, maxUploadDate, FORMAT, 1);
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, retrofit2.Response<PhotoResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Code: " + response.code());
                    return;
                }

                photoLiveData.setValue(response.body().getFlickrPhotoObject().getPhotos());
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                apiError.setValue(t.getMessage());
            }
        });
    }

    public LiveData<List<Photo>> getPhotoLiveData() {
        return photoLiveData;
    }

    class FlickrRequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            request = request.newBuilder()
                    .header("Accept", "application/json")
                    .method(request.method(), request.body())
                    .build();

            return chain.proceed(request);
        }
    }

    public MutableLiveData<String> getApiError() {
        return apiError;
    }
}
