package com.bulentoral.financeapp.ui.transfer.transeferInfo

import com.bulentoral.financeapp.ui.Account.AccountViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bulentoral.financeapp.ui.Account.AccountRepository

class TransactionInfoViewModelFactory(private val repository: TransferInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransferInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransferInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}