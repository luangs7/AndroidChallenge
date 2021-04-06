package com.luan.avatarlist.data.repository

import com.luan.avatarlist.data.dao.GitUserDao
import com.luan.avatarlist.data.service.GitUserService
import com.luan.avatarlist.domain.interactor.GitUserUseCase
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import com.luan.common.extension.isNotFound
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.security.InvalidParameterException

@ExperimentalCoroutinesApi
class GitUserRepositoryImpl(
    private val dao: GitUserDao,
    private val service: GitUserService
) : GitUserRepository {

    override suspend fun deleteUser(user: GitUser): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        dao.deleteUser(user.id!!)
        emit(Resource.success(true))
    }.flowOn(Dispatchers.Default)

    override suspend fun saveUser(user: GitUser) = dao.saveUser(user)

    override suspend fun getUser(login: String): Flow<Resource<GitUser>> = flow {
        emit(Resource.loading())
        dao.getUser(login).let {
            it?.let {
                emit(Resource.cache(it))
            } ?: kotlin.run {
                val response = service.getUser(login)
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        dao.saveUser(user)
                        emit(Resource.success(user))
                    } ?: emit(Resource.error<GitUser>(InvalidParameterException()))
                } else {
                    val exception =
                        if (response.isNotFound()) GitUserUseCase.GitUserNotFoundException()
                        else Exception(response.errorBody().toString())

                    emit(Resource.error<GitUser>(exception))
                }
            }
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun getListUser(): Flow<Resource<List<GitUser>>> = flow {
        emit(Resource.loading())
        dao.getListUser().collect {
            emit(Resource.success(it))
        }
    }.flowOn(Dispatchers.Default)


}