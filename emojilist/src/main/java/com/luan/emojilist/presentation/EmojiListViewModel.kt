package com.luan.emojilist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.extension.toSingleEvent
import com.luan.common.helper.SingleLiveEvent
import com.luan.emojilist.domain.GetListEmojiUseCase
import com.luan.emojilist.domain.GetRandomEmojiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmojiListViewModel(
    private val getListEmojiUseCase: GetListEmojiUseCase,
    private val getRandomEmojiUseCase: GetRandomEmojiUseCase
) : ViewModel() {

    private val _getListEmojiResource =
        MutableLiveData<Resource<List<Emoji>>>()
    val getListEmojiResource = _getListEmojiResource.toSingleEvent()


    private val _getRandomEmojiResource = MutableLiveData<Resource<Emoji>>()
    val getRandomEmojiResource = _getRandomEmojiResource.toSingleEvent()

    val hasEmoji = MutableLiveData<Boolean>(false)

    fun getList() {
        viewModelScope.launch {
            getListEmojiUseCase(Unit).collect {
                _getListEmojiResource.postValue(it)
                if(it.status == Resource.Status.SUCCESS)
                    hasEmoji.postValue(it.data?.isNotEmpty())
            }
        }
    }

    fun getRandomEmoji() {
        viewModelScope.launch {
            getRandomEmojiUseCase(Unit).collect {
                _getRandomEmojiResource.postValue(it)
                if(it.status == Resource.Status.SUCCESS)
                    hasEmoji.postValue(it.data != null)
            }
        }
    }

    fun onEmojiClick() {
        if (hasEmoji.value == true) getRandomEmoji()
        else getList()
    }
}
