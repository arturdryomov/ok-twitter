package com.github.ming13.oktwitter.rx

import java.util.concurrent.TimeUnit

import rx.Observable
import rx.functions.Func1

class ObservableStrategies
{
    companion object Defaults {
        val retryStartCount = 1
        val retryFinishCount = 3

        val retryIntervalSeconds = 3
    }

    class ExponentialBackoffStrategy : Func1<Observable<out Throwable>, Observable<*>>
    {
        private val retryStartCount: Int
        private val retryFinishCount: Int

        private val retryIntervalSeconds: Int

        constructor() {
            this.retryStartCount = Defaults.retryStartCount
            this.retryFinishCount = Defaults.retryFinishCount

            this.retryIntervalSeconds = Defaults.retryIntervalSeconds
        }

        override fun call(throwableObservable: Observable<out Throwable>): Observable<*> {
            return throwableObservable
                .zipWith(Observable.range(retryStartCount, retryFinishCount)) { throwable, retryCount ->
                    retryCount
                }
                .flatMap { retryCount ->
                    Observable.timer(Math.pow(retryIntervalSeconds.toDouble(), retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
                }
        }
    }
}