package com.meghana.loginandreg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meghana.loginandreg.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var username = MutableStateFlow("")
    var password = MutableStateFlow("")

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onLoginClick() {
        val u = username.value
        val p = password.value

        if (u.isEmpty() || p.isEmpty()) {
            _uiState.value = LoginUiState.Error("Username and password cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val user = repository.getUser(u).firstOrNull()
            if ((user != null) && (user.password == p)) {
                repository.setLoggedInUser(u)
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error("Invalid username or password")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    sealed class LoginUiState {
        object Idle : LoginUiState()
        object Loading : LoginUiState()
        object Success : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
}
