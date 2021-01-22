package com.luan.emojilist.core

import android.app.Application
import com.luan.common.di.NetworkModule
import com.luan.emojilist.dependency.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    NetworkModule.dependencyModule,
                    databaseModule
                )
            )
        }
    }

}