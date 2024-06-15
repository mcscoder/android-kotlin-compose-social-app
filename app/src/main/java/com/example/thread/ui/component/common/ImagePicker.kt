package com.example.thread.ui.component.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberMultipleImagePicker(
    onImageFilesChange: (List<ByteArray>) -> Unit,
): () -> Unit {
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
    return fun() = launcher.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )
}

@Composable
fun rememberSingleImagePicker(
    onImageFilesChange: (ByteArray) -> Unit,
): () -> Unit {
    val contentResolver = LocalContext.current.contentResolver
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                contentResolver.openInputStream(it)?.use { inputStream ->
                    val imageFile = inputStream.readBytes()
                    onImageFilesChange(imageFile)
                }
            }
        }
    )
    return fun() = launcher.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )
}
