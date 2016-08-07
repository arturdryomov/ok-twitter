package com.github.ming13.oktwitter.repository.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface StreamingApi
{
    @GET("/1.1/statuses/sample.json")
    @Streaming
    fun getSampleTweets(): Call<ResponseBody>
}
