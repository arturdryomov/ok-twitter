package com.github.ming13.oktwitter.repository.http

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HttpLogger : HttpLoggingInterceptor.Logger
{
    companion object {
        val tag = "API"
    }

    override fun log(message: String) {
        Timber.tag(tag).d(message)
    }
}