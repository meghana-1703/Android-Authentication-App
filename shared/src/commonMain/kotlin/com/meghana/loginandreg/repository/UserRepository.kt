package com.meghana.loginandreg.repository

import com.meghana.loginandreg.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUser(user: User)
    fun getUser(username: String): Flow<User?>
    fun getAllUsers(): Flow<List<User>>
    suspend fun clearSession()
    fun getLoggedInUser(): Flow<User?>
    suspend fun setLoggedInUser(username: String?)
}
