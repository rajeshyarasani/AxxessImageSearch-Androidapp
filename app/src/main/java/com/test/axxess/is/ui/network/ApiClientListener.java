package com.test.axxess.is.ui.network;

import retrofit2.Call;;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
*  ApiClientListener interface, declare Retrofit APi call methods.
*/
public interface ApiClientListener {

    @GET("gallery/search/1")
    Call<ImageSearchResponse> getImageSearchResponse(@Query("q") String searchHint);

}