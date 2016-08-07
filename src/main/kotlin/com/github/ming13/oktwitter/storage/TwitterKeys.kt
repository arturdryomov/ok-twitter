package com.github.ming13.oktwitter.storage

import com.github.ming13.oktwitter.BuildConfig

class TwitterKeys
{
    fun consumerKey(): String {
        return BuildConfig.TWITTER_CONSUMER_KEY
    }

    fun consumerSecret(): String {
        return BuildConfig.TWITTER_CONSUMER_SECRET
    }

    fun accessToken(): String {
        return BuildConfig.TWITTER_ACCESS_TOKEN
    }

    fun accessTokenSecret(): String {
        return BuildConfig.TWITTER_ACCESS_TOKEN_SECRET
    }
}