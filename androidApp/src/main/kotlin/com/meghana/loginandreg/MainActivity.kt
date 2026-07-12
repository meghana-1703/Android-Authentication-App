package com.meghana.loginandreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.meghana.loginandreg.datastore.createDataStore
import com.meghana.loginandreg.repository.UserRepositoryImpl
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class MainActivity : ComponentActivity() {
    
    companion object {
        private var instance: DataStore<Preferences>? = null
        
        fun getDataStore(context: android.content.Context): DataStore<Preferences> {
            return instance ?: createDataStore(context.applicationContext).also { instance = it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val userRepository = UserRepositoryImpl(getDataStore(this))

        setContent {
            App(userRepository)
        }
    }
}
