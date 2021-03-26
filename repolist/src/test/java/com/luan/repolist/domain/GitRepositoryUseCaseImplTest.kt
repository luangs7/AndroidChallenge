package com.luan.repolist.domain

import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.repolist.data.repository.GitReposRepository
import com.luan.repolist.domain.interactor.GitRepositoryUseCase
import com.luan.repolist.domain.interactor.GitRepositoryUseCaseImpl
import com.luan.repolist.domain.model.Repository
import com.luan.repolist.helper.DataHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import net.bytebuddy.asm.Advice
import org.junit.Assert
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito

class GitRepositoryUseCaseImplTest : AutoCloseKoinTest() {

    private val repository = Mockito.mock(GitReposRepository::class.java)
    private val useCase : GitRepositoryUseCase by lazy { GitRepositoryUseCaseImpl(repository) }

    private val flow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.listOfRepository))
    }

    private val flowException = kotlinx.coroutines.flow.flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.error<List<Repository>>(Throwable()))
    }

    @Test
    fun getListFromService() {
        runBlocking {
            Mockito.`when`(repository.getList(DataHelper.USER_REPO,0,10))
                .thenReturn(flow)
            val data = repository.getList(DataHelper.USER_REPO,0,10)
                .take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert list is not empty", false, data.last().data.isNullOrEmpty())
        }
    }


    @Test
    fun getListFromService_with_Error() {
        runBlocking {
            Mockito.`when`(repository.getList(DataHelper.USER_REPO,0,10))
                .thenReturn(flowException)
            val data = repository.getList(DataHelper.USER_REPO,0,10)
                .take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.ERROR, data.last().status)
        }
    }
}