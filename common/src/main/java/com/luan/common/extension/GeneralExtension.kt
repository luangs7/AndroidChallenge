package com.luan.common.extension

import android.view.View
import androidx.fragment.app.Fragment
import com.luan.common.base.Resource

fun Fragment.handleLoading(status:Resource.Status, view:View){
    if (status == Resource.Status.LOADING) view.show()
    else view.hide()
}