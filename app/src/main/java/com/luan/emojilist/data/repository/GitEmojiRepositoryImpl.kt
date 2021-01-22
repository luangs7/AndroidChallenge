package com.luan.emojilist.data.repository

import com.luan.common.base.Resource
import com.luan.common.data.performGetOperation
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.service.GitEmojiService
import com.luan.emojilist.domain.model.Emoji
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.security.InvalidParameterException

class GitEmojiRepositoryImpl(
        private val service: GitEmojiService,
        private val dao:GitEmojiDao
) : GitEmojiRepository {

    override suspend fun getListEmoji(): Flow<Resource<List<Emoji>>> = performGetOperation(
        databaseQuery = { dao.getList() },
        networkCall = { service.getEmojiList() },
        saveCallResult = { dao.saveEmojiList(it) }
    )

    override suspend fun getRandomEmoji(): Flow<Resource<Emoji>> = flow {
        emit(Resource.loading())
        dao.getRandomEmoji().collect {
            emit(Resource.success(it))
        }
    }

    override suspend fun saveEmojiList(list: List<Emoji>) = dao.saveEmojiList(list)
}