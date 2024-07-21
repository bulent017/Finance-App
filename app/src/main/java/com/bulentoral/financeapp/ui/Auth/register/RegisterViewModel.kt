package com.bulentoral.financeapp.ui.Auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulentoral.financeapp.model.User
import com.bulentoral.financeapp.ui.Auth.AuthRepository
import kotlinx.coroutines.launch

// RegisterViewModel.kt
class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _registerResult = MutableLiveData<Pair<Boolean, String?>>()
    val registerResult: LiveData<Pair<Boolean, String?>> get() = _registerResult

    fun registerUser(user: User, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.registerUser(user, password) { success, message ->
                _isLoading.value = false
                _registerResult.value = Pair(success, message)
            }
        }
    }
}
