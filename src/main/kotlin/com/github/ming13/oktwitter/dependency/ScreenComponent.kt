package com.github.ming13.oktwitter.dependency

import com.github.ming13.oktwitter.activity.TweetsActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = arrayOf(
    ScreenModule::class
))
interface ScreenComponent
{
    fun inject(activity: TweetsActivity)
}