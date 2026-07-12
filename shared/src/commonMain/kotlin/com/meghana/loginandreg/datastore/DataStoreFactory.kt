package com.meghana.loginandreg.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(context: Any? = null): DataStore<Preferences>

val DATASTORE_NAME = "user_prefs"
val DATASTORE_FILE_NAME = "$DATASTORE_NAME.preferences_pb"
