package com.luan.common.data

import com.luan.common.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

fun <T> performGetOperation(
    databaseQuery: suspend () -> Flow<T?>,
    networkCall: suspend () -> Response<T>,
    saveCallResult: suspend (T) -> Unit
): Flow<Resource<T>> = flow {
    emit(Resource.loading())
    databaseQuery.invoke().map {
        it?.let {
            emit(Resource.cache(it))
        } ?: kotlin.run {
            try {
                val response = networkCall.invoke()
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        saveCallResult(result)
                        emit(Resource.success(result))
                    }
                } else {
                    throw Exception(response.errorBody().toString())
                }
            } catch (e: Exception) {
                emit(Resource.error(e))
            }
        }
    }
}


class ResourceEmptyException: Exception()