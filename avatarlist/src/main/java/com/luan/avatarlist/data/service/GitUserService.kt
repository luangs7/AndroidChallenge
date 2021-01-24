package com.luan.avatarlist.data.service

import com.luan.avatarlist.domain.model.GitUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitUserService {

    @GET("user/{query}")
    suspend fun getUser(@Path("query") query:String):Response<GitUser>

}