package com.example.thread.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.ui.component.text.TextBody

@Composable
fun ThreadActionButtons(
    thread: ThreadResponse,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onReplyClick: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    ableToReply: Boolean = true,
    iconSize: Dp = 19.dp,
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete confirmation") },
            text = { Text("Are you sure to delete?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDeleteConfirmed()
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconClickable(
                imageVector = if (thread.overview.favorite.isFavorited) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                tint = if (thread.overview.favorite.isFavorited) Color.Red else Color.DarkGray,
                onClick = {
                    onFavoriteClick(!thread.overview.favorite.isFavorited)
                },
                size = iconSize,
            )
            TextBody(text = thread.overview.favorite.count.toString())
        }
        if (ableToReply) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconClickable(
                    imageVector = Icons.Outlined.ModeComment,
                    tint = Color.DarkGray,
                    onClick = { onReplyClick() },
                    size = iconSize,
                )
                TextBody(text = thread.overview.reply.count.toString())
            }
        }
        Box {
            IconClickable(
                imageVector = Icons.Filled.MoreHoriz,
                tint = Color.Gray,
                onClick = { expanded = true },
                size = iconSize,
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog = true
                }) {
                    Text("Delete")
                }
            }
        }
    }
}
