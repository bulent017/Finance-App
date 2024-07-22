package com.bulentoral.financeapp.ui.transfer.transferControl

import android.util.Log
import com.bulentoral.financeapp.model.BankAccount
import com.bulentoral.financeapp.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransferRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getBankAccountByIban(iban: String): BankAccount? {
        try {
            val result = db.collection("bankAccounts")
                .whereEqualTo("iban", iban)
                .get()
                .await()

            if (result.documents.isNotEmpty()) {
                return result.documents[0].toObject(BankAccount::class.java)
            } else {
                Log.d("Firestore", "No documents found for IBAN: $iban")
                return null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching bank account by IBAN", e)
            return null
        }
    }

    suspend fun getUserById(userId: String): User? {
        val document = db.collection("users").document(userId).get().await()
        return if (document.exists()) {
            document.toObject(User::class.java)
        } else {
            null
        }
    }
}