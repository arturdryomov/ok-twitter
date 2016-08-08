package com.github.ming13.oktwitter.presenter

import android.os.Bundle
import com.github.ming13.oktwitter.contract.TweetsContract
import com.github.ming13.oktwitter.dependency.annotation.AsyncScheduler
import com.github.ming13.oktwitter.dependency.annotation.PerScreen
import com.github.ming13.oktwitter.dependency.annotation.ViewScheduler
import com.github.ming13.oktwitter.repository.TweetsRepository
import com.github.ming13.oktwitter.repository.model.Tweet
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.*
import java.util.concurrent.*
import javax.inject.*

@PerScreen
class TweetsPresenter : TweetsContract.Presenter
{
    companion object {
        val stateKeyTweets = "tweets"
    }

    private val tweetsRepository: TweetsRepository

    private val asyncScheduler: Scheduler
    private val viewScheduler: Scheduler

    private val subscription: CompositeSubscription = CompositeSubscription()

    private var view: TweetsContract.View? = null

    var tweets: List<Tweet> = emptyList()

    @Inject
    constructor(tweetsRepository: TweetsRepository, @AsyncScheduler asyncScheduler: Scheduler, @ViewScheduler viewScheduler: Scheduler) {
        this.tweetsRepository = tweetsRepository

        this.asyncScheduler = asyncScheduler
        this.viewScheduler = viewScheduler
    }

    private fun fetchTweets() {
        if (tweets.isEmpty()) {
            view?.showProgress()
        } else {
            view?.appendTweets(tweets)
            view?.showContent()
        }

        subscription.add(createTweetsObservable().subscribe(object : Observer<Tweet> {
            override fun onNext(tweet: Tweet) {
                tweets = tweets.plus(tweet)

                view?.showContent()
                view?.appendTweets(listOf(tweet))
            }

            override fun onError(e: Throwable) {
                Timber.d(e, "Tweets fetching failed.")

                view?.showError()
            }

            override fun onCompleted() {
            }
        }))
    }

    private fun createTweetsObservable(): Observable<Tweet> {
        // Take a single tweet per second instead of showing Twitter entropy as is.

        return tweetsRepository.getTweets()
            .sample(1, TimeUnit.SECONDS)
            .subscribeOn(asyncScheduler)
            .observeOn(viewScheduler)
    }

    override fun onRetry() {
        fetchTweets()
    }

    override fun attachView(view: TweetsContract.View) {
        this.view = view

        fetchTweets()
    }

    override fun detachView() {
        this.view = null

        subscription.clear()
    }

    @SuppressWarnings("unchecked")
    override fun restoreState(state: Bundle?) {
        state?.let {
            val tweets = it.getSerializable(stateKeyTweets) as ArrayList<Tweet>

            this.tweets = this.tweets.plus(tweets)
        }
    }

    override fun saveState(state: Bundle?) {
        val tweets = ArrayList<Tweet>(tweets)

        state?.putSerializable(stateKeyTweets, tweets)
    }
}