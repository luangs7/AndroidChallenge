package com.luan.avatarlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luan.avatarlist.domain.interactor.GitUserDeleteUseCase
import com.luan.avatarlist.domain.interactor.GitUserListUseCase
import com.luan.avatarlist.domain.interactor.GitUserUseCase
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.base.Resource
import com.luan.common.domain.Emoji
import com.luan.common.extension.toSingleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GitUserViewModel(
    private val gitUserListUseCase: GitUserListUseCase,
    private val gitUserDeleteUseCase: GitUserDeleteUseCase,
    private val gitUserUseCase: GitUserUseCase

) : ViewModel() {

    private val _getAvatarList = MutableLiveData<Resource<List<GitUser>>>()
    val getAvatarList = _getAvatarList.toSingleEvent()

    private val _getGitUser = MutableLiveData<Resource<GitUser>>()
    val getGitUser = _getGitUser.toSingleEvent()

    private val _deleteUser = MutableLiveData<Resource<Boolean>>()
    val deleteUser = _deleteUser.toSingleEvent()

    val mutableQueryValue = MutableLiveData<String>()


    fun getAvatarList() {
        viewModelScope.launch {
            gitUserListUseCase(Unit).collect {
                _getAvatarList.postValue(it)
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            mutableQueryValue.value?.let { query ->
                gitUserUseCase(query).collect {
                    _getGitUser.postValue(it)
                }
            }
        }
    }

    fun deleteGitUser(user: GitUser) {
        viewModelScope.launch {
            gitUserDeleteUseCase(user).collect {
                _deleteUser.postValue(it)
            }
        }
    }

    fun setQueryValue(value:String){
        mutableQueryValue.value = value
        getUser()
    }
}