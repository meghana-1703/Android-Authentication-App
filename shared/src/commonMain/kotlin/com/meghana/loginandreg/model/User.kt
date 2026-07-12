package com.meghana.loginandreg.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val fullName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)
