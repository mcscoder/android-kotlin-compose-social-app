package com.example.thread.data.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.thread.dataStore
import com.example.thread.ui.screen.ViewModelProviderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserPreferences(private val context: Context) {
    val userId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }

    fun clearUser() {
        CoroutineScope(Dispatchers.IO).launch {
            clearUserId()
            // Also clear all data of all view model
            ViewModelProviderManager.clear()
        }
    }

    fun setUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            setUserId(userId)

        }
    }

    private suspend fun setUserId(userId: Int = 1) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    private suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
        }
    }

    companion object {
        private val KEY_USER_ID = intPreferencesKey("key_user_id")
    }
}
