package com.example.thread.ui.component.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberToast(): (text: String) -> Unit {
    val context = LocalContext.current
    return fun(text: String) { Toast.makeText(context, text, Toast.LENGTH_LONG).show() }
}
