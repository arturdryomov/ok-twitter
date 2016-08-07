package com.github.ming13.oktwitter

import com.github.ming13.oktwitter.repository.api.StreamingApi
import com.github.ming13.oktwitter.repository.TweetsRepository
import com.github.ming13.oktwitter.repository.model.Tweet
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import rx.observers.TestSubscriber

class TweetsRepositoryTest
{
    @Rule @JvmField
    val server: MockWebServer = MockWebServer()

    private lateinit var tweetsRepository: TweetsRepository

    @Before
    fun setUp() {
        val apiCreator = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .build()

        val api = apiCreator.create(StreamingApi::class.java)

        tweetsRepository = TweetsRepository(api)
    }

    @Test
    fun justSomething() {
        val response = MockResponse()
            .setBody("{'text': 'text', 'user': {'screen_name': 'name', 'profile_image_url_https': 'avatar'}}")

        server.enqueue(response)

        val subscriber = TestSubscriber<Tweet>()

        tweetsRepository.getTweets().subscribe(subscriber)

        subscriber.assertCompleted()
    }
}