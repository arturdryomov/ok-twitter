package com.github.ming13.oktwitter.repository.http

import com.github.ming13.oktwitter.storage.TwitterKeys
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders
import okhttp3.Interceptor
import okhttp3.Response

class HttpAuthenticator : Interceptor
{
    companion object {
        val headerAuthentication = "Authorization"
    }

    private val keys: TwitterKeys

    constructor(keys: TwitterKeys) {
        this.keys = keys
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val authenticatedRequest = originalRequest
            .newBuilder()
            .addHeader(headerAuthentication, createAuthenticationHeader(
                originalRequest.url().toString(),
                originalRequest.method()
            ))
            .build()

        return chain.proceed(authenticatedRequest)
    }

    fun createAuthenticationHeader(requestUrl: String, requestMethod: String): String {
        return OAuth1aHeaders().getAuthorizationHeader(
            TwitterAuthConfig(keys.consumerKey(), keys.consumerSecret()),
            TwitterAuthToken(keys.accessToken(), keys.accessTokenSecret()),
            null, requestMethod, requestUrl, emptyMap()
        )
    }
}