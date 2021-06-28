package com.elapp.booque.base

import android.app.Application
import com.elapp.booque.BuildConfig
import timber.log.Timber

class BooqueApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}