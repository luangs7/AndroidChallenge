package com.luan.emojilist.data.service

import com.luan.common.domain.Emoji
import retrofit2.Response
import retrofit2.http.GET

interface GitEmojiService {
    @GET("emojis")
    suspend fun getEmojiList():Response<List<Emoji>>
}