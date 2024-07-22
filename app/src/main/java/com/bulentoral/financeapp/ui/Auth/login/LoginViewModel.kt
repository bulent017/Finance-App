package com.bulentoral.financeapp.ui.Auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulentoral.financeapp.ui.Auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _loginResult = MutableLiveData<Pair<Boolean, String?>>()
    val loginResult: LiveData<Pair<Boolean, String?>> get() = _loginResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.loginUser(email, password) { success, message ->
                _isLoading.value = false
                _loginResult.value = Pair(success, message)
            }
        }
    }

    fun rememberMe(context: Context, isRemembered: Boolean) {
        viewModelScope.launch {
            authRepository.rememberMe(context, isRemembered)
        }
    }
}
