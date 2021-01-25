package com.luan.repolist.domain.interactor

import com.luan.common.base.Resource
import com.luan.common.interactor.UseCase
import com.luan.repolist.data.repository.GitReposRepository
import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GitRepositoryUseCaseImpl(private val repository: GitReposRepository) : GitRepositoryUseCase() {
    override suspend fun execute(params: Int): Flow<Resource<List<Repository>>> =
        repository.getList(USER_GOOGLE, params, LIMIT_LIST).catch { emit(Resource.error(it)) }

}