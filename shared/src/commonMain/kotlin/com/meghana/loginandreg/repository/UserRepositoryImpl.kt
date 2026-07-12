package com.meghana.loginandreg.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.meghana.loginandreg.model.User
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

class UserRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : UserRepository {

    private val usersKey = stringPreferencesKey("users")
    private val loggedInUserKey = stringPreferencesKey("logged_in_user")

    override suspend fun saveUser(user: User) {
        dataStore.edit { prefs ->
            val currentUsersJson = prefs[usersKey] ?: "[]"
            val users = Json.decodeFromString<List<User>>(currentUsersJson).toMutableList()
            users.add(user)
            prefs[usersKey] = Json.encodeToString(users)
        }
    }

    override fun getUser(username: String): Flow<User?> = dataStore.data.map { prefs ->
        val currentUsersJson = prefs[usersKey] ?: "[]"
        val users = Json.decodeFromString<List<User>>(currentUsersJson)
        users.find { it.username == username }
    }

    override fun getAllUsers(): Flow<List<User>> = dataStore.data.map { prefs ->
        val currentUsersJson = prefs[usersKey] ?: "[]"
        Json.decodeFromString<List<User>>(currentUsersJson)
    }

    override suspend fun clearSession() {
        dataStore.edit { it.remove(loggedInUserKey) }
    }

    override fun getLoggedInUser(): Flow<User?> = dataStore.data.map { prefs ->
        val username = prefs[loggedInUserKey] ?: return@map null
        val currentUsersJson = prefs[usersKey] ?: "[]"
        val users = Json.decodeFromString<List<User>>(currentUsersJson)
        users.find { it.username == username }
    }

    override suspend fun setLoggedInUser(username: String?) {
        dataStore.edit { prefs ->
            if (username == null) {
                prefs.remove(loggedInUserKey)
            } else {
                prefs[loggedInUserKey] = username
            }
        }
    }
}
