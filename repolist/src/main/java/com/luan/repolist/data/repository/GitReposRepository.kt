package com.luan.repolist.data.repository

import com.luan.common.base.Resource
import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface GitReposRepository {

    suspend fun getList(
        user: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<Repository>>>

}