package com.luan.emojilist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.emojilist.domain.GetListEmojiUseCase
import com.luan.emojilist.domain.GetRandomEmojiUseCase
import com.luan.emojilist.helper.CoroutinesTestRule
import com.luan.emojilist.helper.DataHelper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito


class EmojiListViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val testRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val listUseCase = Mockito.mock(GetListEmojiUseCase::class.java)
    private val randomUseCase = Mockito.mock(GetRandomEmojiUseCase::class.java)

    private val viewModel: EmojiListViewModel by lazy {
        EmojiListViewModel(
            listUseCase,
            randomUseCase
        )
    }

    private val listFlow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.listOfEmoji))
    }
    private val randomFlow = flow {
        emit(Resource.loading())
        delay(1000)
        emit(Resource.success(DataHelper.emojiData))
    }

    @Test
    fun getList_with_success() {
        val observer = viewModel.getListEmojiResource.test()

        testRule.runBlockingTest {
            whenever(listUseCase.invoke(Unit)).thenReturn(listFlow)

            viewModel.getList()

            verify(listUseCase, times(1)).invoke(Unit)
        }

        val history = observer
            .assertHasValue()
            .assertHistorySize(2)
            .valueHistory()

        assert(history.first().status == Resource.Status.LOADING)
        assert(history[1].status == Resource.Status.SUCCESS)
        assert(history[1].data?.first()?.key == DataHelper.emojiData.key)
    }

    @Test
    fun getList_with_error() {
        val observer = viewModel.getListEmojiResource.test()

        testRule.runBlockingTest {
            whenever(listUseCase.invoke(Unit)).thenReturn(flow { emit(Resource.error<List<Emoji>>(Exception())) })
            viewModel.getList()
            verify(listUseCase, times(1)).invoke(anyOrNull())
        }

        observer
            .assertHasValue()
            .assertValue { it.status == Resource.Status.ERROR }
    }

    @Test
    fun getRandom_with_success() {
        val observer = viewModel.getRandomEmojiResource.test()


        testRule.runBlockingTest {
            whenever(randomUseCase.invoke(anyOrNull())).thenReturn(randomFlow)
            viewModel.getRandomEmoji()
            verify(randomUseCase, times(1)).invoke(anyOrNull())
        }

        val history = observer
            .assertHasValue()
            .valueHistory()

        assert(history.first().status == Resource.Status.LOADING)
        assert(history[1].status == Resource.Status.SUCCESS)
        assert(history[1].data?.key == DataHelper.emojiData.key)


    }


    @Test
    fun getRandom_with_error() {
        val observer = viewModel.getRandomEmojiResource.test()


        testRule.runBlockingTest {
            whenever(randomUseCase.invoke(anyOrNull())).thenReturn(flow { emit(Resource.error<Emoji>(Exception())) })
            viewModel.getRandomEmoji()
            verify(randomUseCase, times(1)).invoke(anyOrNull())
        }

        observer
            .assertHasValue()
            .assertValue { it.status == Resource.Status.ERROR }
    }

}