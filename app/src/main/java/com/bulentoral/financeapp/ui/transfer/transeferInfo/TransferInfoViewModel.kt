package com.bulentoral.financeapp.ui.transfer.transeferInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulentoral.financeapp.model.BankAccount
import com.bulentoral.financeapp.model.Transaction
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class TransferInfoViewModel(private val transeferInfoRepository: TransferInfoRepository): ViewModel() {
    private val _bankAccounts = MutableLiveData<List<BankAccount>>()
    val bankAccounts: LiveData<List<BankAccount>> = _bankAccounts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _transactionResult = MutableLiveData<Result<Unit>>()
    val transactionResult: LiveData<Result<Unit>> = _transactionResult

    init {
        fetchBankAccounts()
    }

    private fun fetchBankAccounts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            _isLoading.value = true
            viewModelScope.launch {
                val result = transeferInfoRepository.fetchBankAccounts(userId)
                result.onSuccess { accounts ->
                    _bankAccounts.value = accounts
                }.onFailure {
                    // Hata durumu
                }
                _isLoading.value = false
            }
        }
    }

    fun transferAmount(senderAccount: BankAccount, receiverIban: String, amount: Double, note: String?) {
        viewModelScope.launch {
            _isLoading.value = true

            val receiverResult = transeferInfoRepository.getBankAccountByIban(receiverIban)
            if (receiverResult.isSuccess && receiverResult.getOrNull() != null) {
                val receiverAccount = receiverResult.getOrNull()!!

                if (senderAccount.accountBalance != null && senderAccount.accountBalance!! >= amount) {
                    val newSenderBalance = senderAccount.accountBalance!! - amount
                    val newReceiverBalance = receiverAccount.accountBalance!! + amount

                    val transaction = Transaction(
                        senderUid = FirebaseAuth.getInstance().currentUser?.uid,
                        senderAccountId = senderAccount.accountId,
                        receiverIban = receiverIban,
                        amount = amount,
                        note = note,
                        timestamp = System.currentTimeMillis()
                    )

                    val transactionResult = transeferInfoRepository.createTransaction(transaction)
                    if (transactionResult.isSuccess) {
                        val updateSenderResult = transeferInfoRepository.updateBankAccountBalance(senderAccount.accountId!!,
                            newSenderBalance.toLong()
                        )
                        val updateReceiverResult = transeferInfoRepository.updateBankAccountBalance(receiverAccount.accountId!!,
                            newReceiverBalance.toLong()
                        )

                        if (updateSenderResult.isSuccess && updateReceiverResult.isSuccess) {
                            _transactionResult.value = Result.success(Unit)
                        } else {
                            _transactionResult.value = Result.failure(Exception("Failed to update account balances"))
                        }
                    } else {
                        _transactionResult.value = transactionResult
                    }
                } else {
                    _transactionResult.value = Result.failure(Exception("Insufficient balance"))
                }
            } else {
                _transactionResult.value = Result.failure(Exception("Receiver account not found"))
            }

            _isLoading.value = false
        }
    }
}
