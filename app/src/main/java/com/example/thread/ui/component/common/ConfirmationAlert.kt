package com.example.thread.ui.component.common

import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

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
