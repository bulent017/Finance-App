package com.bulentoral.financeapp.ui.AddAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddAccountViewModelFactory(private val repository: AddAccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddAccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
