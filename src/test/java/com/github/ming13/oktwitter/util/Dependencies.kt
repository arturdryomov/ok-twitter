package com.github.ming13.oktwitter.util

import rx.schedulers.Schedulers

class Dependencies
{
    companion object {
        val scheduler = Schedulers.immediate()
    }
}