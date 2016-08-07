package com.github.ming13.oktwitter.dependency

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