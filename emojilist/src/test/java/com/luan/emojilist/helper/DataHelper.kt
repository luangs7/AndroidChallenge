package com.luan.emojilist.helper

import com.luan.common.domain.Emoji
import com.luan.common.helper.errorResponseBody
import retrofit2.Response

object DataHelper{
    val emojiData by lazy { Emoji("0", "Teste") }
    val listOfEmoji by lazy { listOf(emojiData) }
    val responseFail = Response.error<List<Emoji>>(500, errorResponseBody)
    val responseListSuccess = Response.success(listOfEmoji)
}


