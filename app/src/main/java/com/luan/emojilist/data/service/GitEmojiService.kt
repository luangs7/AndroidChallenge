package com.luan.emojilist.data.service

import com.luan.emojilist.domain.model.Emoji
import retrofit2.Response

interface GitEmojiService {

    suspend fun getEmojiList():Response<List<Emoji>>
}