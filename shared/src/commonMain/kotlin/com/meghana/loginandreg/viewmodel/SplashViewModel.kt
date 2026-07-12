package com.meghana.loginandreg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meghana.loginandreg.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(2500.milliseconds) // 2-3 seconds as requested
            val loggedInUser = repository.getLoggedInUser().firstOrNull()
            if (loggedInUser != null) {
                _navigationEvent.emit(SplashNavigation.ToHome)
            } else {
                _navigationEvent.emit(SplashNavigation.ToLogin)
            }
        }
    }

    sealed class SplashNavigation {
        object ToLogin : SplashNavigation()
        object ToHome : SplashNavigation()
    }
}
