package com.bulentoral.financeapp.ui.transfer.transferControl


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TransferViewModel : ViewModel() {

    private val repository = TransferRepository()

    private val _userFullName = MutableLiveData<String>()
    val userFullName: LiveData<String> get() = _userFullName

    fun searchIban(iban: String) {
        viewModelScope.launch {
            val bankAccount = repository.getBankAccountByIban("TR$iban")
            if (bankAccount != null) {
                val user = repository.getUserById(bankAccount.userId!!)
                if (user != null) {
                    _userFullName.postValue("${user.fullName} ${user.lastName}")
                } else {
                    _userFullName.postValue("User not found")
                }
            } else {
                _userFullName.postValue("Bank account not found")
            }
        }
    }
}
