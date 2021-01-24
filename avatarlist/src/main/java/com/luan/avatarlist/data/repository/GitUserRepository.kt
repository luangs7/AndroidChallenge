package com.luan.avatarlist.data.repository

import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import kotlinx.coroutines.flow.Flow

interface GitUserRepository {

    suspend fun deleteUser(user: GitUser):Flow<Resource<Boolean>>
    suspend fun saveUser(user: GitUser)
    suspend fun getUser(login:String): Flow<Resource<GitUser>>
    suspend fun getListUser(): Flow<Resource<List<GitUser>>>

}