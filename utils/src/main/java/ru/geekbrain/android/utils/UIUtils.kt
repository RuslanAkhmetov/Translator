package ru.geekbrain.android.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog


fun getStubAlertDialog(context: Context): AlertDialog {
    return getAlertDialog(context, null, null)
}

fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog =
     AlertDialog.Builder(context)
        .setCancelable(true)
        .setPositiveButton(R.string.dialog_button_cancel){dialog, _ -> dialog.dismiss()}
        .setTitle(title ?: context.getString(R.string.error_textview_stub))
        .setMessage(message ?: "")
        .create()







