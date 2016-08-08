package com.github.ming13.oktwitter.util

import java.util.*

class Generator
{
    companion object {
        fun generateString(): String {
            return UUID.randomUUID().toString()
        }
    }
}