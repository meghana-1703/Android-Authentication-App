package com.meghana.loginandreg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meghana.loginandreg.model.User
import com.meghana.loginandreg.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    var fullName = MutableStateFlow("")
    var username = MutableStateFlow("")
    var email = MutableStateFlow("")
    var phoneNumber = MutableStateFlow("")
    var password = MutableStateFlow("")
    var confirmPassword = MutableStateFlow("")

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onRegisterClick() {
        if (!validateFields()) return

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            
            val existingUser = repository.getUser(username.value).firstOrNull()
            if (existingUser != null) {
                _uiState.value = RegisterUiState.Error("Username already exists")
                return@launch
            }

            val newUser = User(
                fullName = fullName.value,
                username = username.value,
                email = email.value,
                phoneNumber = phoneNumber.value,
                password = password.value
            )
            repository.saveUser(newUser)
            _uiState.value = RegisterUiState.Success
        }
    }

    private fun validateFields(): Boolean {
        if (fullName.value.isEmpty() || username.value.isEmpty() || email.value.isEmpty() ||
            phoneNumber.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()
        ) {
            _uiState.value = RegisterUiState.Error("All fields are mandatory")
            return false
        }

        if (!isValidEmail(email.value)) {
            _uiState.value = RegisterUiState.Error("Invalid email address")
            return false
        }

        if (!phoneNumber.value.all { it.isDigit() }) {
            _uiState.value = RegisterUiState.Error("Phone number should contain only digits")
            return false
        }

        if (password.value.length < 8) {
            _uiState.value = RegisterUiState.Error("Password must be at least 8 characters")
            return false
        }

        if (password.value != confirmPassword.value) {
            _uiState.value = RegisterUiState.Error("Passwords do not match")
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }

    sealed class RegisterUiState {
        object Idle : RegisterUiState()
        object Loading : RegisterUiState()
        object Success : RegisterUiState()
        data class Error(val message: String) : RegisterUiState()
    }
}
