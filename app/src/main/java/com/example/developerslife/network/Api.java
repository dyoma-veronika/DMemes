package com.example.developerslife.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("/{type}/{page}?json=true")
    Single<Result> getPosts(@Path("type") String type, @Path("page") int page);
}
