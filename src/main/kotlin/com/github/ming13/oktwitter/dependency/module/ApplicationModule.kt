package com.github.ming13.oktwitter.dependency.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule
{
    val context: Context

    constructor(application: Application) {
        this.context = application.applicationContext
    }

    @Provides
    fun provideContext(): Context {
        return context
    }
}