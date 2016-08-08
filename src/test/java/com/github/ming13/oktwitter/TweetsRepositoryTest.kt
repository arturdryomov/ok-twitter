package com.github.ming13.oktwitter

import com.github.ming13.oktwitter.dependency.RepositoryModule
import com.github.ming13.oktwitter.repository.api.StreamingApi
import com.github.ming13.oktwitter.repository.TweetsRepository
import com.github.ming13.oktwitter.repository.model.Tweet
import com.github.ming13.oktwitter.repository.model.TweetUser
import com.github.ming13.oktwitter.util.Generator
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import rx.observers.TestSubscriber
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

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

        tweetsRepository = TweetsRepository(api, RepositoryModule().provideJsonReader())
    }

    @Test
    fun `returns valid data on successful response`() {
        val tweetContent = Generator.generateString()
        val tweetUserName = Generator.generateString()
        val tweetUserNickname = Generator.generateString()
        val tweetUserAvatar = Generator.generateString()

        val tweetResponse = MockResponse()
            .setBody(createTweetJson(tweetContent, tweetUserName, tweetUserNickname, tweetUserAvatar))

        server.enqueue(tweetResponse)

        val subscriber = TestSubscriber<Tweet>()

        tweetsRepository.getTweets().subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValueCount(1)
        subscriber.assertValue(Tweet(tweetContent, TweetUser(tweetUserName, tweetUserNickname, tweetUserAvatar)))
        subscriber.assertCompleted()
    }

    @Test
    fun `returns valid data on successful response with unknown items`() {
        val tweetContent = Generator.generateString()
        val tweetUserName = Generator.generateString()
        val tweetUserNickname = Generator.generateString()
        val tweetUserAvatar = Generator.generateString()

        val tweetResponse = MockResponse()
            .setBody(createTweetJson(tweetContent, tweetUserName, tweetUserNickname, tweetUserAvatar) + "\r\n" + Generator.generateString())

        server.enqueue(tweetResponse)

        val subscriber = TestSubscriber<Tweet>()

        tweetsRepository.getTweets().subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValueCount(1)
        subscriber.assertValue(Tweet(tweetContent, TweetUser(tweetUserName, tweetUserNickname, tweetUserAvatar)))
        subscriber.assertCompleted()
    }

    private fun createTweetJson(content: String, userName: String, userNickname: String, userAvatar: String): String {
        return "{'text': '$content', 'user': {'name': '$userName', 'screen_name': '$userNickname', 'profile_image_url_https': '$userAvatar'}}"
    }

    @Test
    fun `returns error on unsuccessful response`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)

        server.enqueue(response)

        val subscriber = TestSubscriber<Tweet>()

        tweetsRepository.getTweets().subscribe(subscriber)

        subscriber.assertNoValues()
    }
}