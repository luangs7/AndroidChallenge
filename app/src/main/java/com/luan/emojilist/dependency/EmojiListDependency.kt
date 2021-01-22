package com.luan.emojilist.dependency

import android.app.Application
import androidx.room.Room
import com.luan.emojilist.core.GitEmojiDatabase
import com.luan.emojilist.data.dao.GitEmojiDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): GitEmojiDatabase {
        return Room.databaseBuilder(application, GitEmojiDatabase::class.java, "emoji")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: GitEmojiDatabase): GitEmojiDao {
        return  database.emojiDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}