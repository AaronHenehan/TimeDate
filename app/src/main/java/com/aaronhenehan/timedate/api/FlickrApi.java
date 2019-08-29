package com.aaronhenehan.timedate.api;

import com.aaronhenehan.timedate.model.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("rest/")
    Call<PhotoResponse> searchPhotosByTag(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("tags") String tags,
            @Query("format") String format,
            @Query("nojsoncallback") int callback);

    @GET("rest/")
    Call<PhotoResponse> searchPhotosByUploadDate(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("min_upload_date") long minUploadDate,
            @Query("mac_upload_date") long maxUploadDate,
            @Query("format") String format,
            @Query("nojsoncallback") int callback);

}
