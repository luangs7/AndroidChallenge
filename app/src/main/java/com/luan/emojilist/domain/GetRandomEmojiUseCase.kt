package com.luan.emojilist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.interactor.UseCase
import kotlinx.coroutines.flow.Flow

abstract class GetRandomEmojiUseCase : UseCase<Unit,Flow<Resource<Emoji>>>()