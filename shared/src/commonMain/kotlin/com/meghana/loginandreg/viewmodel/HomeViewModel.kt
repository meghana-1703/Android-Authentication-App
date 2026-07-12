package com.meghana.loginandreg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meghana.loginandreg.model.User
import com.meghana.loginandreg.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    val loggedInUser: StateFlow<User?> = repository.getLoggedInUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onLogoutClick() {
        viewModelScope.launch {
            repository.setLoggedInUser(null)
        }
    }
}
