package com.github.ming13.oktwitter.repository

import com.github.ming13.oktwitter.repository.api.StreamingApi
import com.github.ming13.oktwitter.repository.model.Tweet
import com.google.gson.Gson
import rx.Observable
import java.io.*
import javax.inject.Inject

class TweetsRepository
{
    val api: StreamingApi

    @Inject
    constructor(api: StreamingApi) {
        this.api = api
    }

    fun getTweets(): Observable<Tweet> {
        return Observable.create { subscriber ->
            val response = api.getSampleTweets().execute()

            val reader = BufferedReader(response.body().charStream())

            var tweet = reader.readLine()

            while (tweet != null) {
                if (subscriber.isUnsubscribed) {
                    break
                }

                subscriber.onNext(Gson().fromJson(tweet, Tweet::class.java))

                tweet = reader.readLine()
            }

            subscriber.onCompleted()
        }
    }
}