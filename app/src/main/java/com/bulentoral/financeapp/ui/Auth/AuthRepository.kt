package com.bulentoral.financeapp.ui.Auth

import android.content.Context
import com.bulentoral.financeapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registerUser(user: User, password: String, callback: (Boolean, String?) -> Unit) {
        user.email?.let {
            auth.createUserWithEmailAndPassword(it, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        userId?.let {
                            user.userId = it
                            firestore.collection("users").document(it).set(user.toMap())
                                .addOnSuccessListener {
                                    callback(true, null)
                                }
                                .addOnFailureListener { e ->
                                    callback(false, e.message)
                                }
                        }
                    } else {
                        callback(false, task.exception?.message)
                    }
                }
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun rememberMe(context: Context, isRemembered: Boolean) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isRemembered", isRemembered)
        editor.apply()
    }

    fun isRemembered(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isRemembered", false)
    }
    fun signOut(callback: (Boolean, String?) -> Unit) {
        try {
            auth.signOut()
            callback(true, null)
        } catch (e: Exception) {
            callback(false, e.message)
        }
    }
}

