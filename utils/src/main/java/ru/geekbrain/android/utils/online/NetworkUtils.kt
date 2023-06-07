@file:Suppress("DEPRECATION")

package ru.geekbrain.android.utils

import android.content.Context
import android.net.ConnectivityManager

fun isOnline(context: Context): Boolean {
    val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    val status = connectivityManager.activeNetworkInfo
    return status != null && status.isConnected
}
