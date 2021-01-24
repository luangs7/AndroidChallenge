package com.luan.emojilist.dependency

import android.app.Application
import androidx.room.Room
import com.luan.common.extension.resolveRetrofit
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.dao.GitEmojiDatabase
import com.luan.emojilist.data.repository.GitEmojiRepository
import com.luan.emojilist.data.repository.GitEmojiRepositoryImpl
import com.luan.emojilist.data.service.GitEmojiService
import com.luan.emojilist.data.service.GitEmojiServiceMock
import com.luan.emojilist.domain.GetListEmojiUseCase
import com.luan.emojilist.domain.GetListEmojiUseCaseImpl
import com.luan.emojilist.domain.GetRandomEmojiUseCase
import com.luan.emojilist.domain.GetRandomEmojiUseCaseImpl
import com.luan.emojilist.presentation.EmojiListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val emojiDatabaseModule = module {

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


    single<GetListEmojiUseCase> {
        GetListEmojiUseCaseImpl(
            get()
        )
    }
    single<GetRandomEmojiUseCase> {
        GetRandomEmojiUseCaseImpl(
            get()
        )
    }
    single<GitEmojiRepository> {
        GitEmojiRepositoryImpl(
            get(),
            get()
        )
    }
    factory<GitEmojiService> { resolveRetrofit() ?: GitEmojiServiceMock() }
    viewModel { EmojiListViewModel(get(),get()) }
}