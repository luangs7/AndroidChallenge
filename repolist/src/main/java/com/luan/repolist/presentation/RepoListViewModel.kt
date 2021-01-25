package com.luan.repolist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luan.common.base.Resource
import com.luan.common.extension.toSingleEvent
import com.luan.repolist.domain.interactor.GitRepositoryUseCase
import com.luan.repolist.domain.model.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RepoListViewModel(private val gitRepositoryUseCase: GitRepositoryUseCase) : ViewModel() {

    private val _getRepositoryResponse = MutableLiveData<Resource<List<Repository>>>()
    val getRepositoryResponse = _getRepositoryResponse.toSingleEvent()

    private val page = MutableLiveData<Int>(1)


    fun getList(){
        viewModelScope.launch {
            gitRepositoryUseCase(page.value ?: 1).collect {
                _getRepositoryResponse.postValue(it)

                if(it.status == Resource.Status.SUCCESS){
                    page.postValue(page.value?.plus(1))
                }
            }
        }
    }
}