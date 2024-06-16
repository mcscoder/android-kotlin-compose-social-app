package com.example.thread.ui.screen.secondary.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.thread.data.viewmodel.threaddata.ThreadsData

class SettingViewModel : ViewModel() {
    val displayFavorited = mutableStateOf(false)
    val threadsFavoritedData = ThreadsData()

    val displaySaved = mutableStateOf(false)
    val threadsSavedData = ThreadsData()
}
