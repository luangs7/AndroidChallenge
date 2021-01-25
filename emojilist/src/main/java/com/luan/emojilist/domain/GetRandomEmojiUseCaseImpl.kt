package com.luan.emojilist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.interactor.UseCase
import com.luan.emojilist.data.repository.GitEmojiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@ExperimentalCoroutinesApi
class GetRandomEmojiUseCaseImpl(private val repository: GitEmojiRepository) : GetRandomEmojiUseCase(){
    override suspend fun execute(params: Unit): Flow<Resource<Emoji>> {
        return repository.getRandomEmoji().catch { emit(Resource.error(it)) }
    }
 }