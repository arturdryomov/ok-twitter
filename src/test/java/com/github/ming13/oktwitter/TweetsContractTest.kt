package com.github.ming13.oktwitter

import com.github.ming13.oktwitter.contract.TweetsContract
import com.github.ming13.oktwitter.presenter.TweetsPresenter
import com.github.ming13.oktwitter.repository.TweetsRepository
import com.github.ming13.oktwitter.repository.model.Tweet
import com.github.ming13.oktwitter.repository.model.TweetUser
import com.github.ming13.oktwitter.util.Dependencies
import com.github.ming13.oktwitter.util.Generator
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable

class TweetsContractTest
{
    private lateinit var repository: TweetsRepository

    private lateinit var view: TweetsContract.View
    private lateinit var presenter: TweetsContract.Presenter

    @Before
    fun setUp() {
        repository = mock()

        view = mock()
        presenter = TweetsPresenter(repository, Dependencies.scheduler, Dependencies.scheduler)
    }

    @Test
    fun `shows progress before everything`() {
        whenever(repository.getTweets())
            .thenReturn(Observable.empty())

        presenter.attachView(view)

        verify(view).showProgress()
    }

    @Test
    fun `shows error for errors`() {
        whenever(repository.getTweets())
            .thenReturn(Observable.error(RuntimeException("Cake is a lie.")))

        presenter.attachView(view)

        verify(view).showProgress()
        verify(view).showError()
    }

    @Test
    fun `shows content for valid data`() {
        val tweet = Tweet(
            Generator.generateString(),
            TweetUser(Generator.generateString(), Generator.generateString(), Generator.generateString())
        )

        whenever(repository.getTweets())
            .thenReturn(Observable.just(tweet))

        presenter.attachView(view)

        verify(view).showProgress()
        verify(view).showContent()
    }
}