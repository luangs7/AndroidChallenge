package com.luan.repolist.dependency


import com.luan.common.extension.resolveRetrofit
import com.luan.repolist.data.repository.GitReposRepository
import com.luan.repolist.data.repository.GitReposRepositoryImpl
import com.luan.repolist.data.service.GitRepositoryService
import com.luan.repolist.data.service.GitRepositoryServiceMock
import com.luan.repolist.domain.interactor.GitRepositoryUseCase
import com.luan.repolist.domain.interactor.GitRepositoryUseCaseImpl
import com.luan.repolist.presentation.RepoListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val repolistModule = module {

    single<GitRepositoryUseCase> {
        GitRepositoryUseCaseImpl(
            get()
        )
    }

    factory<GitReposRepository> { GitReposRepositoryImpl(get()) }
    factory<GitRepositoryService> { resolveRetrofit() ?: GitRepositoryServiceMock() }
    viewModel { RepoListViewModel(get()) }
}