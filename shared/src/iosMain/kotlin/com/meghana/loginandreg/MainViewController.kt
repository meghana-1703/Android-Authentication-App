package com.meghana.loginandreg

import androidx.compose.ui.window.ComposeUIViewController
import com.meghana.loginandreg.datastore.createDataStore
import com.meghana.loginandreg.repository.UserRepositoryImpl

@Suppress("unused")
fun MainViewController() = ComposeUIViewController {
    val dataStore = createDataStore()
    val userRepository = UserRepositoryImpl(dataStore)
    App(userRepository)
}
