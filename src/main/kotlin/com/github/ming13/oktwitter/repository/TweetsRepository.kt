package com.github.ming13.oktwitter.repository

import com.github.ming13.oktwitter.repository.api.StreamingApi
import com.github.ming13.oktwitter.repository.model.Tweet
import com.github.ming13.oktwitter.rx.ObservableStrategies
import com.google.gson.Gson
import com.google.gson.JsonParseException
import rx.Observable
import java.io.*
import javax.inject.Inject

open class TweetsRepository
{
    val api: StreamingApi

    val jsonReader: Gson

    @Inject
    constructor(api: StreamingApi, jsonReader: Gson) {
        this.api = api

        this.jsonReader = jsonReader
    }

    open fun getTweets(): Observable<Tweet> {
        val tweetsObservable: Observable<Tweet> = Observable.create { subscriber ->
            val tweetsResponse = api.getSampleTweets().execute()

            BufferedReader(tweetsResponse.body().charStream()).use { tweetsReader ->
                var tweetText = tweetsReader.readLine()

                while (tweetText != null) {
                    if (subscriber.isUnsubscribed) {
                        break
                    }

                    subscriber.onNext(convertTweet(tweetText))
                    tweetText = tweetsReader.readLine()
                }

                subscriber.onCompleted()
            }
        }

        return tweetsObservable
            .filter { tweet -> tweet != null }
            .retryWhen(ObservableStrategies.ExponentialBackoffStrategy())
    }

    private fun convertTweet(tweetText: String): Tweet? {
        try {
            return jsonReader.fromJson(tweetText, Tweet::class.java)
        } catch (e: JsonParseException) {
            // Basically just ignore anything else than a tweet for now.

            return null
        }
    }
}