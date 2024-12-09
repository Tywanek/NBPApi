package com.radlab.nbpapi.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}
