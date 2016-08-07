package com.github.ming13.oktwitter.adapter

import android.content.*
import android.support.v7.widget.*
import android.text.util.*
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ming13.oktwitter.R
import com.github.ming13.oktwitter.graphics.PicassoCircleTransformation
import com.github.ming13.oktwitter.repository.model.Tweet
import com.squareup.picasso.Picasso

class TweetsAdapter(context: Context) : RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>()
{
    class TweetViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        @BindView(R.id.image_user_avatar)
        lateinit var userAvatar: ImageView

        @BindView(R.id.text_user_name)
        lateinit var userName: TextView

        @BindView(R.id.text_user_nickname)
        lateinit var userNickname: TextView

        @BindView(R.id.text_content)
        lateinit var content: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bindTweet(tweet: Tweet) {
            bindUser(tweet)
            bindContent(tweet)
        }

        fun bindUser(tweet: Tweet) {
            Picasso.with(context())
                .load(tweet.user.originalAvatar())
                .fit()
                .centerCrop()
                .transform(PicassoCircleTransformation(context()))
                .into(userAvatar)

            userName.text = tweet.user.name
            userNickname.text = "@${tweet.user.nickname}"
        }

        fun bindContent(tweet: Tweet) {
            content.text = tweet.text

            Linkify.addLinks(content, Linkify.WEB_URLS)
        }

        fun context(): Context {
            return itemView.context
        }
    }

    private val viewInflater: LayoutInflater

    private val tweets: MutableList<Tweet>

    init {
        viewInflater = LayoutInflater.from(context)

        tweets = mutableListOf()
    }

    fun append(appendedTweets: List<Tweet>) {
        val tweetsPosition = tweets.size

        tweets.addAll(appendedTweets)

        notifyItemRangeInserted(tweetsPosition, appendedTweets.size)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onCreateViewHolder(viewContainer: ViewGroup, viewType: Int): TweetViewHolder {
        return TweetViewHolder(viewInflater.inflate(R.layout.view_tweet, viewContainer, false))
    }

    override fun onBindViewHolder(tweetViewHolder: TweetViewHolder, position: Int) {
        tweetViewHolder.bindTweet(tweets[position])
    }
}