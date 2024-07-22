package com.bulentoral.financeapp.ui.transfer.transeferInfo

import com.bulentoral.financeapp.model.BankAccount
import com.bulentoral.financeapp.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransferInfoRepository(private val firestore: FirebaseFirestore) {
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

    suspend fun createTransaction(transaction: Transaction): Result<Unit> {
        return try {
            val transactionRef = firestore.collection("transactions").document()
            transaction.transactionId = transactionRef.id
            transactionRef.set(transaction.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBankAccountBalance(accountId: String, newBalance: Long): Result<Unit> {
        return try {
            firestore.collection("bankAccounts").document(accountId)
                .update("accountBalance", newBalance)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBankAccountByIban(iban: String): Result<BankAccount?> {
        return try {
            val result = firestore.collection("bankAccounts")
                .whereEqualTo("iban", "TR"+iban)
                .get()
                .await()
            if (result.documents.isNotEmpty()) {
                Result.success(result.documents[0].toObject(BankAccount::class.java))
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
