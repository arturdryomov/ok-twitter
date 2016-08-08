package com.github.ming13.oktwitter.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TweetUser
(
    @SerializedName("name")
    val name: String,

    @SerializedName("screen_name")
    val nickname: String,

    @SerializedName("profile_image_url_https")
    val avatar: String
) : Serializable
{
    fun originalAvatar(): String {
        return avatar.replace("_normal", "")
    }
}