package com.bulentoral.financeapp.ui.AddAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulentoral.financeapp.model.BankAccount
import kotlinx.coroutines.launch


class AddAccountViewModel(private val addAccountRepository: AddAccountRepository) : ViewModel() {

    private val _uploadProgress = MutableLiveData<Boolean>()
    val uploadProgress: LiveData<Boolean> = _uploadProgress

    private val _uploadError = MutableLiveData<String?>()
    val uploadError: LiveData<String?> = _uploadError

    fun saveAccount(bankAccount: BankAccount) {
        _uploadProgress.value = true
        viewModelScope.launch {
            val result = addAccountRepository.saveBankAccount(bankAccount)
            result.onSuccess {
                _uploadProgress.value = false
            }.onFailure { e ->
                _uploadProgress.value = false
                _uploadError.value = e.message
            }
        }
    }
}
