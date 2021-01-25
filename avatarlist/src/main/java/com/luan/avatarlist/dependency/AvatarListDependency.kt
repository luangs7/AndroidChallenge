package com.luan.avatarlist.dependency

import android.app.Application
import androidx.room.Room
import com.luan.avatarlist.data.dao.GitUserDao
import com.luan.avatarlist.data.dao.GitUserDatabase
import com.luan.avatarlist.data.repository.GitUserRepository
import com.luan.avatarlist.data.repository.GitUserRepositoryImpl
import com.luan.avatarlist.data.service.GitUserService
import com.luan.avatarlist.data.service.GitUserServiceMock
import com.luan.avatarlist.domain.interactor.*
import com.luan.avatarlist.presentation.GitUserViewModel
import com.luan.common.extension.resolveRetrofit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val avatarDatabaseModule = module {

    fun provideDatabase(application: Application): GitUserDatabase {
        return Room.databaseBuilder(application, GitUserDatabase::class.java, "gituser")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: GitUserDatabase): GitUserDao {
        return  database.gitUserDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }


    single<GitUserUseCase> {
        GitUserUseCaseImpl(
            get()
        )
    }
    single<GitUserListUseCase> {
        GitUserListUseCaseImpl(
            get()
        )
    }
    single<GitUserDeleteUseCase> {
        GitUserDeleteUseCaseImpl(
            get()
        )
    }

    factory<GitUserRepository> { GitUserRepositoryImpl(get(),get()) }
    factory<GitUserService> { resolveRetrofit() ?: GitUserServiceMock() }
    viewModel { GitUserViewModel(get(),get(),get()) }
}