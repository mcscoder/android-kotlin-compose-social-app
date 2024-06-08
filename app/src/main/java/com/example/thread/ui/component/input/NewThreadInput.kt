package com.example.thread.ui.component.input

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.common.ThreadVerticalDivider
import com.example.thread.ui.component.image.ThreadInputImage
import com.example.thread.ui.component.user.UserAvatar
import com.example.thread.ui.component.user.UsernameClickable
import com.example.thread.ui.screen.GlobalViewModelProvider

@SuppressLint("Range")
@Composable
fun NewThreadInput(
    modifier: Modifier = Modifier,
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    imageFiles: List<ByteArray>,
    onImageFilesChange: (List<ByteArray>) -> Unit,
    onRemoveImageClick: (index: Int) -> Unit,
    placeHolder: String = "What's new?",
) {
    val user = GlobalViewModelProvider.getInstance().user.collectAsState().value
    val contentResolver = LocalContext.current.contentResolver

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            val imageFiles: MutableList<ByteArray> = mutableListOf()
            uris.forEach { uri ->
                val inputStream = contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val imageFile = inputStream.readBytes()
                    imageFiles.add(imageFile)
                }
            }
            onImageFilesChange(imageFiles)
        }

    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserAvatar(avatarURL = user.imageUrl)
            ThreadVerticalDivider()
        }
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            UsernameClickable(
                username = user.firstName,
                onClick = { /*TODO*/ })
            BorderlessTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onTextChange,
                placeHolder = placeHolder
            )
            if (imageFiles.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .height(300.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    imageFiles.forEachIndexed { index, bytes ->
                        ThreadInputImage(bytes, onRemoveImageClick = { onRemoveImageClick(index) })
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {1
                IconClickable(
                    imageVector = Icons.Outlined.Image,
                    tint = Color.DarkGray,
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    })

            }
        }
    }
}

// Photo picker google document
// https://developer.android.com/training/data-storage/shared/photopicker#kotlin
