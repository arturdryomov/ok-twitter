package com.github.ming13.oktwitter

import android.app.Application
import com.github.ming13.oktwitter.dependency.ApplicationComponent
import com.github.ming13.oktwitter.dependency.ApplicationModule
import com.github.ming13.oktwitter.dependency.DaggerApplicationComponent
import com.github.ming13.oktwitter.dependency.RepositoryModule
import com.github.ming13.oktwitter.util.Android
import timber.log.Timber

class OkTwitterApplication : Application()
{
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        setUpComponent()

        setUpLogging()
    }

    private fun setUpComponent() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }

    private fun setUpLogging() {
        if (Android.isDebugging()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}