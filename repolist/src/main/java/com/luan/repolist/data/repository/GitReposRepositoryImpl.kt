package com.luan.repolist.data.repository

import com.luan.common.base.Resource
import com.luan.repolist.data.service.GitRepositoryService
import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GitReposRepositoryImpl(private val service: GitRepositoryService) : GitReposRepository {

    override suspend fun getList(
        user: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.loading())

        val response = service.getRepositories(user, page, limit)
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        } else {
            emit(Resource.error(Exception(response.errorBody().toString())))
        }

    }.flowOn(Dispatchers.Default)

}