package com.bulentoral.financeapp.ui.AddAccount

import com.bulentoral.financeapp.model.BankAccount
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class AddAccountRepository(private val firestore: FirebaseFirestore) {

    suspend fun saveBankAccount(bankAccount: BankAccount): Result<String> {
        return try {
            val documentReference = firestore.collection("bankAccounts").document()
            val accountId = documentReference.id
            val newBankAccount = bankAccount.copy(accountId = accountId)
            documentReference.set(newBankAccount.toMap()).await()
            Result.success(accountId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
