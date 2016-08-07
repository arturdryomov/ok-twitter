package com.github.ming13.oktwitter.contract

import com.github.ming13.oktwitter.presenter.LifecyclePresenter
import com.github.ming13.oktwitter.repository.model.Tweet

interface TweetsContract
{
    interface Presenter : LifecyclePresenter<View>
    {
        fun onRetry()
    }

    interface View
    {
        fun showProgress()
        fun showContent()
        fun showError()

        fun appendTweets(tweets: List<Tweet>)
    }
}