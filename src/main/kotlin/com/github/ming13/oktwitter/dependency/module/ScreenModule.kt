package com.github.ming13.oktwitter.dependency.module

import android.app.*
import com.github.ming13.oktwitter.dependency.annotation.PerScreen
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