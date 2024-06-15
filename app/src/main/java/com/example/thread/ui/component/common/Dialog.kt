package com.example.thread.ui.component.common

import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ConfirmationAlert(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    title: String,
    text: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onDismiss()
            },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onConfirmClick()
                }) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onCancelClick()
                }) {
                    Text(cancelText)
                }
            }
        )
    }
}

@Composable
fun rememberAlertDialog(
    title: String,
    text: String,
    onConfirmClick: () -> Unit = {},
    confirmationText: String = "OK",
): () -> Unit {
    val display = remember {
        mutableStateOf(false)
    }

    if (display.value) {
        AlertDialog(
            onDismissRequest = { display.value = false },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        onConfirmClick()
                        display.value = false
                    }
                ) {
                    Text(confirmationText)
                }
            }
        )
    }

    return fun() { display.value = true }
}
