package com.example.thread.data.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.thread.dataStore
import com.example.thread.ui.screen.ViewModelProviderManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserPreferences(private val context: Context) {
    val userId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }
    val searchHistory: Flow<List<String>> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[KEY_SEARCH_HISTORY] ?: "[]"
        Gson().fromJson(jsonString, Array<String>::class.java).toList()
    }

    fun clearUser() {
        CoroutineScope(Dispatchers.IO).launch {
            clearUserId()
            clearSearchHistory()
            // Also clear all data of all view model
            ViewModelProviderManager.clear()
        }
    }

    fun setUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            setUserId(userId)
        }
    }

    private suspend fun setUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    private suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
        }
    }

    fun addSearchHistoryItem(item: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentHistory = getSearchHistory()
            val updatedHistory = currentHistory.toMutableList().apply { add(item) }
            setSearchHistory(updatedHistory)
        }
    }

    private suspend fun getSearchHistory(): List<String> {
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[KEY_SEARCH_HISTORY] ?: "[]"
        return Gson().fromJson(jsonString, Array<String>::class.java).toList()
    }

    private suspend fun setSearchHistory(history: List<String>) {
        val jsonString = Gson().toJson(history)
        context.dataStore.edit { preferences ->
            preferences[KEY_SEARCH_HISTORY] = jsonString
        }
    }

    fun clearSearchHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences.remove(KEY_SEARCH_HISTORY)
            }
        }
    }

    companion object {
        private val KEY_USER_ID = intPreferencesKey("key_user_id")
        private val KEY_SEARCH_HISTORY = stringPreferencesKey("key_search_history")
    }
}
