package com.bulentoral.financeapp.ui.Account


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bulentoral.financeapp.ui.Account.AccountRepository

class AccountViewModelFactory(private val repository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
