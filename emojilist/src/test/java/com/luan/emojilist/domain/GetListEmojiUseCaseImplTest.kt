package com.luan.emojilist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.repository.GitEmojiRepositoryImpl
import com.luan.emojilist.helper.DataHelper
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

class GetListEmojiUseCaseImplTest : AutoCloseKoinTest() {

    private val repository = Mockito.mock(GitEmojiRepositoryImpl::class.java)
    private val useCase : GetListEmojiUseCaseImpl

    private val flow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.listOfEmoji))
    }

    private val flowException = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.error<List<Emoji>>(Throwable()))
    }

    init {
        useCase = GetListEmojiUseCaseImpl(repository)
    }


    @Test
    fun getList() {
        runBlocking {
            Mockito.`when`(repository.getListEmoji()).thenReturn(flow)
            val data = useCase(Unit).take(2).toList()
            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert list is not empty", false, data.last().data.isNullOrEmpty())
        }
    }

    @Test
    fun getListFail() {
        runBlocking {
            Mockito.`when`(repository.getListEmoji()).thenReturn(flowException)
            val data = useCase(Unit).take(2).toList()
            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.ERROR, data.last().status)
        }
    }

}