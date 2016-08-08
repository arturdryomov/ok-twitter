package com.github.ming13.oktwitter.dependency.component

import com.github.ming13.oktwitter.dependency.module.ApplicationModule
import com.github.ming13.oktwitter.dependency.module.RepositoryModule
import com.github.ming13.oktwitter.dependency.module.ScreenModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class,
    RepositoryModule::class
))
interface ApplicationComponent
{
    fun plus(module: ScreenModule): ScreenComponent
}