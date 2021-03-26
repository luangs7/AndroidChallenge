package com.luan.emojilist.data.repository

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.helper.UNKNOW_ERROR
import com.luan.common.helper.errorResponseBody
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.data.service.GitEmojiServiceMock
import com.luan.emojilist.dependency.emojiDatabaseModule
import com.luan.emojilist.helper.DataHelper
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.koin.test.mock.declare
import org.mockito.Mockito.mock
import retrofit2.Response
import org.mockito.Mockito.`when` as whenMock

@OptIn(InternalCoroutinesApi::class)
class GitEmojiRepositoryImplTest : AutoCloseKoinTest() {

    private val service = mock(GitEmojiServiceMock::class.java)
    private val dao = mock(GitEmojiDao::class.java)
    private val repository: GitEmojiRepositoryImpl

    init {
        startKoin {
            modules(listOf(emojiDatabaseModule))
            declare { single { dao } }
        }

        repository = GitEmojiRepositoryImpl(service, get())
    }

    @Test
    fun getListFromService() {
        runBlocking {
            whenMock(dao.getList()).thenReturn(flow { emit(emptyList()) })
            whenMock(service.getEmojiList()).thenReturn(DataHelper.responseListSuccess)
            val data = repository.getListEmoji().take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert list is not empty", false, data.last().data.isNullOrEmpty())
        }
    }

    @Test
    fun getListFromCache() {
        runBlocking {
            whenMock(dao.getList()).thenReturn(flow { emit(DataHelper.listOfEmoji) })
            val data = repository.getListEmoji().take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.CACHE, data.last().status)
            Assert.assertEquals("Assert list is not empty", false, data.last().data.isNullOrEmpty())
        }
    }

    @Test
    fun getListFromServerFail() {
        runBlocking {
            whenMock(dao.getList()).thenReturn(flow { emit(emptyList()) })
            whenMock(service.getEmojiList()).thenReturn( DataHelper.responseFail )
            val data = repository.getListEmoji().take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.ERROR, data.last().status)
            Assert.assertEquals("Assert error message from server", UNKNOW_ERROR, data.last().exception?.message)
        }
    }

    @Test
    fun getRandomEmojiFromDB() {
        runBlocking {
            whenMock(dao.getRandomEmoji()).thenReturn(flow { emit(DataHelper.emojiData) })
            val data = repository.getRandomEmoji().take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert random emoji value", DataHelper.emojiData.key, data.last().data?.key)
        }
    }
}