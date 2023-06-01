package ru.geekbrain.android.translator.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ru.geekbrain.android.translator.R


fun getStubAlertDialog(context: Context): AlertDialog {
    return getAlertDialog(context, null, null)
}

fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog =
     AlertDialog.Builder(context)
        .setCancelable(true)
        .setPositiveButton(R.string.dialog_button_cancell){dialog, _ -> dialog.dismiss()}
        .setTitle(title ?: context.getString(R.string.error_textview_stub))
        .setMessage(message ?: "")
        .create()





