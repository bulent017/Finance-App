package com.bulentoral.financeapp.ui.Account

import com.bulentoral.financeapp.model.BankAccount
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AccountRepository(private val firestore: FirebaseFirestore) {
    suspend fun fetchBankAccounts(userId: String): Result<List<BankAccount>> {
        return try {
            val documents = firestore.collection("bankAccounts")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            val accounts = documents.mapNotNull { it.toObject(BankAccount::class.java) }
            Result.success(accounts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}