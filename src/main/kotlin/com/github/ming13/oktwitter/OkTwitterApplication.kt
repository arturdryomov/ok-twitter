package com.github.ming13.oktwitter

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.github.ming13.oktwitter.dependency.component.ApplicationComponent
import com.github.ming13.oktwitter.dependency.component.DaggerApplicationComponent
import com.github.ming13.oktwitter.dependency.module.ApplicationModule
import com.github.ming13.oktwitter.dependency.module.RepositoryModule
import com.github.ming13.oktwitter.util.Android
import timber.log.Timber

class OkTwitterApplication : Application()
{
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        StrictMode.enableDefaults()
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = setUpComponent().build()

        setUpLogging()
    }

    private fun setUpComponent(): DaggerApplicationComponent.Builder = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .repositoryModule(RepositoryModule())

    private fun setUpLogging() {
        if (Android.isDebugging()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
