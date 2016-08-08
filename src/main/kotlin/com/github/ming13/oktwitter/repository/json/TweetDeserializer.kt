package com.github.ming13.oktwitter.repository.json

import com.github.ming13.oktwitter.repository.model.Tweet
import com.github.ming13.oktwitter.repository.model.TweetUser
import com.google.gson.*
import java.lang.reflect.Type

class TweetDeserializer : JsonDeserializer<Tweet>
{
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Tweet {
        val jsonObject = json.asJsonObject

        if (!isCorrectTweetJson(jsonObject)) {
            throw JsonParseException("Required fields are not available")
        }

        val tweetContent = context.deserialize<String>(jsonObject.get("text"), String::class.java)
        val tweetUser = context.deserialize<TweetUser>(jsonObject.get("user"), TweetUser::class.java)

        return Tweet(tweetContent, tweetUser)
    }

    private fun isCorrectTweetJson(json: JsonObject): Boolean {
        for (jsonField in listOf("text", "user")) {
            if (!json.has(jsonField)) {
                return false
            }
        }

        return true
    }
}