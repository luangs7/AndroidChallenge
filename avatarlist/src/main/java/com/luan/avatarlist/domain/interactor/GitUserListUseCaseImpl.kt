package com.luan.avatarlist.domain.interactor

import com.luan.avatarlist.data.repository.GitUserRepository
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import com.luan.common.interactor.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@ExperimentalCoroutinesApi
class GitUserListUseCaseImpl (private val repository: GitUserRepository)
     : GitUserListUseCase(){
     override suspend fun execute(params: Unit): Flow<Resource<List<GitUser>>> =
         repository.getListUser().catch { emit(Resource.error(it)) }
 }