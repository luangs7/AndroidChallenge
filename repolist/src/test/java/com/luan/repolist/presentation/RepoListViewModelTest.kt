package com.luan.repolist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.repolist.domain.interactor.GitRepositoryUseCase
import com.luan.repolist.domain.model.Repository
import com.luan.repolist.helper.CoroutinesTestRule
import com.luan.repolist.helper.DataHelper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito


class RepoListViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val testRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val listUseCase = Mockito.mock(GitRepositoryUseCase::class.java)

    private val viewModel: RepoListViewModel by lazy {
        RepoListViewModel(
            listUseCase
        )
    }

    private val listFlow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.listOfRepository))
    }


    @Test
    fun get_repo_list_with_success() {
        val observer = viewModel.getRepositoryResponse.test()

        testRule.runBlockingTest {
            whenever(listUseCase.invoke(any())).thenReturn(listFlow)

            viewModel.getList()

            verify(listUseCase, times(1)).invoke(any())
        }

        val history = observer
            .assertHasValue()
            .assertHistorySize(2)
            .valueHistory()

        assert(history.first().status == Resource.Status.LOADING)
        assert(history[1].status == Resource.Status.SUCCESS)
        assert(history[1].data?.first()?.id == DataHelper.repository.id)
    }

    @Test
    fun get_repo_list_with_error() {
        val observer = viewModel.getRepositoryResponse.test()

        testRule.runBlockingTest {
            whenever(listUseCase.invoke(any())).thenReturn(flow { emit(Resource.error<List<Repository>>(Exception())) })
            viewModel.getList()
            verify(listUseCase, times(1)).invoke(any())
        }

        observer
            .assertHasValue()
            .assertValue { it.status == Resource.Status.ERROR }
    }

}