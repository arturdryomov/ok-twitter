package com.github.ming13.oktwitter.presenter

import android.os.Bundle

interface LifecyclePresenter<in V>
{
    fun attachView(view: V)
    fun detachView()

    fun restoreState(state: Bundle?)
    fun saveState(state: Bundle?)
}