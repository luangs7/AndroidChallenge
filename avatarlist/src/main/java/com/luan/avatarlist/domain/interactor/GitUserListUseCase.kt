package com.luan.avatarlist.domain.interactor

import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import com.luan.common.interactor.UseCase
import kotlinx.coroutines.flow.Flow

abstract class GitUserListUseCase : UseCase<Unit,Flow<Resource<List<GitUser>>>>()