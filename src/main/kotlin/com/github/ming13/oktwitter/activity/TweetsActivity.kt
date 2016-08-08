package com.github.ming13.oktwitter.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.github.ming13.oktwitter.OkTwitterApplication
import com.github.ming13.oktwitter.R
import com.github.ming13.oktwitter.adapter.TweetsAdapter
import com.github.ming13.oktwitter.contract.TweetsContract
import com.github.ming13.oktwitter.dependency.module.ScreenModule
import com.github.ming13.oktwitter.presenter.TweetsPresenter
import com.github.ming13.oktwitter.repository.model.Tweet
import com.github.ming13.oktwitter.view.RecyclerViewDividerDecoration
import com.github.ming13.oktwitter.view.ViewAnimator
import javax.inject.*

class TweetsActivity : Activity(), TweetsContract.View
{
    @BindView(R.id.animator)
    lateinit var tweetsAnimator: ViewAnimator

    @BindView(R.id.recycler)
    lateinit var tweetsList: RecyclerView

    // For some reason injecting TweetsContract.Presenter does not work.
    // In such case Dagger requires @Provides declaration.

    @Inject
    lateinit var tweetsPresenter: TweetsPresenter

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_tweets)

        setUpBindings()

        setUpTweetsList()

        setUpDependencies()

        setUpTweetsPresenter(state)
    }

    private fun setUpBindings() {
        ButterKnife.bind(this)
    }

    private fun setUpTweetsList() {
        tweetsList.addItemDecoration(RecyclerViewDividerDecoration(this))
        tweetsList.layoutManager = LinearLayoutManager(this)

        tweetsList.adapter = TweetsAdapter(this)
    }

    private fun setUpDependencies() {
        OkTwitterApplication.applicationComponent
            .plus(ScreenModule(this))
            .inject(this)
    }

    private fun setUpTweetsPresenter(state: Bundle?) {
        tweetsPresenter.restoreState(state)
        tweetsPresenter.attachView(this)
    }

    override fun appendTweets(tweets: List<Tweet>) {
        tweetsAdapter().append(tweets)
    }

    private fun tweetsAdapter(): TweetsAdapter {
        return tweetsList.adapter as TweetsAdapter
    }

    override fun showProgress() {
        tweetsAnimator.show(R.id.progress)
    }

    override fun showContent() {
        tweetsAnimator.show(R.id.container_content)
    }

    override fun showError() {
        tweetsAnimator.show(R.id.container_error)
    }

    @OnClick(R.id.button_retry)
    fun setUpRetry() {
        tweetsPresenter.onRetry()
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)

        tweetsPresenter.saveState(state)
    }

    override fun onDestroy() {
        super.onDestroy()

        tweetsPresenter.detachView()
    }
}
