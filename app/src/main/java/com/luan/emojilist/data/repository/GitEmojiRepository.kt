package com.luan.emojilist.data.repository

import com.luan.common.base.Resource
import com.luan.emojilist.domain.model.Emoji
import kotlinx.coroutines.flow.Flow

interface GitEmojiRepository {

    suspend fun getRandomEmoji():Flow<Resource<Emoji>>
    suspend fun getListEmoji():Flow<Resource<List<Emoji>>>
    suspend fun saveEmojiList(list:List<Emoji>)

}