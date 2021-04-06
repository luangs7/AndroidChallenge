package com.luan.emojilist.data.repository

import com.luan.common.base.Resource
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.service.GitEmojiService
import com.luan.common.domain.Emoji
import com.luan.common.helper.getException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception

@ExperimentalCoroutinesApi
class GitEmojiRepositoryImpl(
    private val service: GitEmojiService,
    private val dao: GitEmojiDao
) : GitEmojiRepository {

    override suspend fun getListEmoji(): Flow<Resource<List<Emoji>>> = flow {
        emit(Resource.loading())
        dao.getList().collect { list ->
            if(list.isEmpty()){
                val remoteList = service.getEmojiList()
                if(remoteList.isSuccessful){
                    remoteList.body()?.let {
                        dao.saveEmojiList(it)
                        emit(Resource.success(it))
                    }
                }else{
                    emit(Resource.error<List<Emoji>>(remoteList.getException()))
                }
            }else{
                emit(Resource.cache(list))
            }
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun getRandomEmoji(): Flow<Resource<Emoji>> = flow {
        emit(Resource.loading())
        dao.getRandomEmoji().collect {
            emit(Resource.success(it))
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun saveEmojiList(list: List<Emoji>) = dao.saveEmojiList(list)
}