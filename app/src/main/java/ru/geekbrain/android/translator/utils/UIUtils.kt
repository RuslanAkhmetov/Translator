package ru.geekbrain.android.translator.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ru.geekbrain.android.translator.R


fun getStubAlertDialog(context: Context): AlertDialog {
    return getAlertDialog(context, null, null)
}

fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog {
    val finalTitle = title ?: context.getString(R.string.error_textview_stub)
    val finalMessage = message ?: ""

    return AlertDialog.Builder(context)
        .setTitle(finalTitle)
        .setMessage(finalMessage)
        .create()
}
