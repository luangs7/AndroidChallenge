package com.luan.common.base

class Resource<T> private constructor(val status: Status, val data: T? = null, val exception: Throwable? = null) {

    enum class Status {
        SUCCESS, CACHE, ERROR, LOADING
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }
        fun <T> cache(data: T?): Resource<T> {
            return Resource(Status.CACHE, data)
        }
        fun <T> error(exception: Throwable?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                exception
            )
        }
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data
            )
        }
    }

    fun <T> transform(data: T? = null) : Resource<T> {
        return Resource(status, data, exception)
    }

}
