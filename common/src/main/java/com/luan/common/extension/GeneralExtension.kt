package com.luan.common.extension

import android.view.View
import androidx.compose.runtime.State
import androidx.fragment.app.Fragment
import com.luan.common.base.Resource
import retrofit2.Response

fun Fragment.handleLoading(status:Resource.Status, view:View){
    if (status == Resource.Status.LOADING) view.show()
    else view.hide()
}

fun <T> Response<T>.isNotFound():Boolean = this.code() == 404

fun <T> State<Resource<T>>.observer(
    onEmpty: (() -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: ((T?) -> Unit)? = null,
    onCache: ((T?) -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null,
    onEveryState: ((Resource.Status) -> Unit)? = null,
) {
    onEveryState?.invoke(value.status)
    when (value.status) {
        Resource.Status.SUCCESS -> onSuccess?.invoke(value.data)
        Resource.Status.CACHE -> onCache?.invoke(value.data)
        Resource.Status.ERROR -> onError?.invoke(value.exception)
        Resource.Status.LOADING -> onLoading?.invoke()
        Resource.Status.EMPTY -> onEmpty?.invoke()
    }
}