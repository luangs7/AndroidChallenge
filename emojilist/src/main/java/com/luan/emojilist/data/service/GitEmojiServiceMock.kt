package com.luan.emojilist.data.service

import com.luan.common.domain.Emoji
import kotlinx.coroutines.delay
import retrofit2.Response

class GitEmojiServiceMock : GitEmojiService {

    override suspend fun getEmojiList(): Response<List<Emoji>> {
        delay(3000)
        return Response.success(listOf(
            Emoji(
                "1",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f948.png?v8"
            ),
            Emoji(
                "2",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f9ee.png?v8"
            ),
            Emoji(
                "3",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f948.png?v8"
            ),
            Emoji(
                "4",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f9ee.png?v8"
            )
        )
        )
    }
}