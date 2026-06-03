package com.bgmi.serverglitch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {

    companion object {
        const val PREFERENCES_NAME = "bgmi_prefs"
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    private val dataStore: DataStore<Preferences> by lazy {
        context.preferencesDataStore
    }

    val tokenFlow: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }
    val userIdFlow: Flow<String?> = dataStore.data.map { it[USER_ID_KEY] }
    val usernameFlow: Flow<String?> = dataStore.data.map { it[USERNAME_KEY] }

    suspend fun saveToken(token: String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveUser(userId: Long, username: String) {
        dataStore.edit {
            it[USER_ID_KEY] = userId.toString()
            it[USERNAME_KEY] = username
        }
    }

    suspend fun clearToken() {
        dataStore.edit { it.clear() }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TokenManager.PREFERENCES_NAME)
