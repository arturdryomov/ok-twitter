package com.github.ming13.oktwitter.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tweet
(
    @SerializedName("text")
    val text: String,

    @SerializedName("user")
    val user: TweetUser
) : Serializable