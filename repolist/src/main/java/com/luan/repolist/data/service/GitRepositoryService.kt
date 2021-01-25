package com.luan.repolist.data.service

import com.luan.repolist.domain.model.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitRepositoryService {

    @GET("users/{user}/repos")
    suspend fun getRepositories(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<Repository>>
}