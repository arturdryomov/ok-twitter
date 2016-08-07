package com.github.ming13.oktwitter.dependency

import android.app.*
import dagger.Module
import dagger.Provides

@Module
class ScreenModule
{
    val activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    @Provides @PerScreen
    fun provideActivity(): Activity {
        return activity
    }
}