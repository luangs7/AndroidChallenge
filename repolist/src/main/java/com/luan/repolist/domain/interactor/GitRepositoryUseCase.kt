package com.luan.repolist.domain.interactor

import com.luan.common.base.Resource
import com.luan.common.interactor.UseCase
import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.flow.Flow

abstract class GitRepositoryUseCase : UseCase<Int,Flow<Resource<List<Repository>>>>() {
    companion object {
        const val LIMIT_LIST = 10
        const val USER_GOOGLE = "google"
        const val STARTING_PAGE_INDEX = 1
    }
}