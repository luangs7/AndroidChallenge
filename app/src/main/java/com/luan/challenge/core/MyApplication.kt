package com.luan.challenge.core

import android.app.Application
import com.luan.avatarlist.dependency.avatarDatabaseModule
import com.luan.common.di.NetworkModule
import com.luan.emojilist.dependency.emojiDatabaseModule
import com.luan.emojilist.dependency.emojiDependency
import com.luan.repolist.dependency.repolistModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    NetworkModule.dependencyModule,
                    emojiDatabaseModule,
                    emojiDependency,
                    avatarDatabaseModule,
                    repolistModule
                )
            )
        }
    }

}