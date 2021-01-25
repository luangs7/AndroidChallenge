package com.luan.avatarlist.data.service

import com.luan.avatarlist.domain.model.GitUser
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random

class GitUserServiceMock: GitUserService {

    override suspend fun getUser(query: String): Response<GitUser> {
        delay(3000)
        return Response.success(
            GitUser("https://avatars.githubusercontent.com/u/16122202?s=460&v=4",Random.nextInt(),"luangs7")
        )
    }
}