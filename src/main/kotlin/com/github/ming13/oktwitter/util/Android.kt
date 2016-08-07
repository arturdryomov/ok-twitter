package com.github.ming13.oktwitter.util

import com.github.ming13.oktwitter.BuildConfig

class Android
{
    companion object {
        fun isDebugging(): Boolean {
            return BuildConfig.DEBUG
        }
    }
}