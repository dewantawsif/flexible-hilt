package me.tawsif.hilt.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.flexible.FlexibleHilt

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FlexibleHilt.init(this)
    }
}
