package com.appstone.newsapiapplication;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIinterface {

//    http://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=e8da45f5458e4543b17ed712bed1581e



    @GET("/v2/top-headlines")
    Call<Result> getNews(@QueryMap Map<String,Object> options);
}
