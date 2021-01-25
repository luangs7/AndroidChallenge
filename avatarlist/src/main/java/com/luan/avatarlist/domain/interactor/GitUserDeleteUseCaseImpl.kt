package com.luan.avatarlist.domain.interactor

import com.luan.avatarlist.data.repository.GitUserRepository
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import com.luan.common.interactor.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@ExperimentalCoroutinesApi
class GitUserDeleteUseCaseImpl (private val repository: GitUserRepository)
     : GitUserDeleteUseCase(){

    override suspend fun execute(params: GitUser): Flow<Resource<Boolean>> =
        repository.deleteUser(params).catch { emit(Resource.error(it)) }
 }