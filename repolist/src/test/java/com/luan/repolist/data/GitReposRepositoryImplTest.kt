package com.luan.repolist.data

import com.luan.common.base.Resource
import com.luan.common.helper.errorResponseBody
import com.luan.repolist.data.repository.GitReposRepository
import com.luan.repolist.data.repository.GitReposRepositoryImpl
import com.luan.repolist.data.service.GitRepositoryService
import com.luan.repolist.data.service.GitRepositoryServiceMock
import com.luan.repolist.helper.DataHelper
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import retrofit2.Response

class GitReposRepositoryImplTest : AutoCloseKoinTest() {


    private val service = Mockito.mock(GitRepositoryServiceMock::class.java)
    private val repository : GitReposRepository by lazy { GitReposRepositoryImpl(service) }

    @Test
    fun getRepository_with_service_success(){
        runBlocking {
            whenever(service.getRepositories(DataHelper.USER_REPO, 0, 10))
                .thenReturn(Response.success(DataHelper.listOfRepository))

            val data = repository.getList(DataHelper.USER_REPO, 0, 10)
                .take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.SUCCESS, data.last().status)
            Assert.assertEquals("Assert list is not empty", false, data.last().data.isNullOrEmpty())
        }
    }

    @Test
    fun getRepository_with_service_error(){
        runBlocking {
            whenever(service.getRepositories(DataHelper.USER_REPO, 0, 10))
                .thenReturn(Response.error(400, errorResponseBody))

            val data = repository.getList(DataHelper.USER_REPO, 0, 10)
                .take(2).toList()

            Assert.assertEquals("Assert has Loading status", Resource.Status.LOADING, data.first().status)
            Assert.assertEquals("Assert list is from Server", Resource.Status.ERROR, data.last().status)
        }
    }
}