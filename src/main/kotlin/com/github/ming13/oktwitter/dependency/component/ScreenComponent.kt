package com.github.ming13.oktwitter.dependency.component

import com.github.ming13.oktwitter.activity.TweetsActivity
import com.github.ming13.oktwitter.dependency.annotation.PerScreen
import com.github.ming13.oktwitter.dependency.module.ScreenModule
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = arrayOf(
    ScreenModule::class
))
interface ScreenComponent
{
    fun inject(activity: TweetsActivity)
}