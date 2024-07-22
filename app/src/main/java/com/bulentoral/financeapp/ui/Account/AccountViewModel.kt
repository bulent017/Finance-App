package com.bulentoral.financeapp.ui.Account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulentoral.financeapp.model.BankAccount
import com.bulentoral.financeapp.ui.AddAccount.AddAccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _bankAccounts = MutableLiveData<List<BankAccount>>()
    val bankAccounts: LiveData<List<BankAccount>> = _bankAccounts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalBalance = MutableLiveData<Long>()
    val totalBalance: LiveData<Long> = _totalBalance

    init {
        fetchBankAccounts()
    }

    public fun fetchBankAccounts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            _isLoading.value = true
            viewModelScope.launch {
                val result = accountRepository.fetchBankAccounts(userId)
                result.onSuccess { accounts ->
                    _bankAccounts.value = accounts
                    calculateTotalBalance(accounts)
                }.onFailure {
                    // Hata durumu
                }
                _isLoading.value = false
            }
        }
    }
    private fun calculateTotalBalance(accounts: List<BankAccount>) {
        val total = accounts.fold(0L) { acc, account ->
            acc + (account.accountBalance?.toLong() ?: 0L)
        }
        _totalBalance.value = total
    }
}