package com.example.lentavk.utils

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(otherFlow: Flow<T>): Flow<T> {
    return merge(this, otherFlow)
}

fun String.encode(): String{
    return Uri.encode(this)
}