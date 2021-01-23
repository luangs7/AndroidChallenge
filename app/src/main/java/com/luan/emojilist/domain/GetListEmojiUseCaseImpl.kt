package com.luan.emojilist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.interactor.UseCase
import com.luan.emojilist.data.repository.GitEmojiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@ExperimentalCoroutinesApi
class GetListEmojiUseCaseImpl(private val repository: GitEmojiRepository) : GetListEmojiUseCase(){
     override suspend fun execute(params: Unit): Flow<Resource<List<Emoji>>> {
        return repository.getListEmoji().catch {
            emit(Resource.error(it))
        }
     }
 }