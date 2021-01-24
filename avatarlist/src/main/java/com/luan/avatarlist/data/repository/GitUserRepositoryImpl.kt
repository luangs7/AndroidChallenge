package com.luan.avatarlist.data.repository

import com.luan.avatarlist.data.dao.GitUserDao
import com.luan.avatarlist.data.service.GitUserService
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.security.InvalidParameterException

class GitUserRepositoryImpl(
    private val dao: GitUserDao,
    private val service: GitUserService
) : GitUserRepository {


    override suspend fun saveUser(user: GitUser) = dao.saveUser(user)

    override suspend fun getUser(login: String): Flow<Resource<GitUser>> = flow {
        emit(Resource.loading())
        dao.getUser(login).collect {
            it?.let { emit(Resource.cache(it)) } ?: kotlin.run {
                val response = service.getUser(login)
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        dao.saveUser(user)
                        emit(Resource.success(user))
                    } ?: emit(Resource.error(InvalidParameterException()))
                } else {
                    emit(Resource.error(Exception(response.errorBody().toString())))
                }
            }

        }
    }

    override suspend fun getListUser(): Flow<Resource<List<GitUser>>> = flow {
        emit(Resource.loading())
        dao.getListUser().collect {
            if (it.isEmpty()) {
                emit(Resource.error(InvalidParameterException()))
            } else {
                emit(Resource.success(it))
            }

        }
    }


}