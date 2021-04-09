package com.luan.repolist.data.service

import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.GET
import kotlin.random.Random

class GitRepositoryServiceMock : GitRepositoryService {

    override suspend fun getRepositories(
        user: String,
        page: Int,
        limit: Int
    ): Response<List<Repository>> {
        delay(4000)
        return Response.success(
            listOf(
                Repository(
                    "google/android_kotlin",
                    Random.nextInt(),
                    false
                ),
                Repository(
                    "google/challenge_android_kotlin",
                    Random.nextInt(),
                    false
                ),
                Repository(
                    "google/desafio_android_kotlin",
                    Random.nextInt(),
                    true
                )
            )
        )
    }
}