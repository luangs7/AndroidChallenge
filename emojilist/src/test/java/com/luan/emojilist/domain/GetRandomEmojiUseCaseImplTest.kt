package com.luan.emojilist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.repository.GitEmojiRepositoryImpl
import com.luan.emojilist.helper.DataHelper
import com.nhaarman.mockitokotlin2.notNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.koin.test.mock.declare
import org.mockito.Mockito

class GetRandomEmojiUseCaseImplTest : AutoCloseKoinTest() {

    private val repository = Mockito.mock(GitEmojiRepositoryImpl::class.java)
    private val useCase : GetRandomEmojiUseCaseImpl

    private val flow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.emojiData))
    }

    private val flowException = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.error<Emoji>(Throwable()))
    }

    init {
        useCase = GetRandomEmojiUseCaseImpl(repository)
    }


    @Test
    fun getRandomEmoji() {
        runBlocking {
            Mockito.`when`(repository.getRandomEmoji()).thenReturn(flow)
            val data = useCase(Unit).take(2).toList()
            Assert.assertEquals("Assert status was loading", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert status is success", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert emoji is correct", DataHelper.emojiData.key, data.last().data?.key)
        }
    }

    @Test
    fun getRandomEmojiFail() {
        runBlocking {
            Mockito.`when`(repository.getRandomEmoji()).thenReturn(flowException)
            val data = useCase(Unit).take(2).toList()
            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.ERROR, data.last().status)
        }
    }

}