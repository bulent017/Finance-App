package com.bulentoral.financeapp.utils

import android.content.Context
import android.widget.Toast
import com.bulentoral.financeapp.ui.Auth.register.RegisterFragment

object ValidationUtils {
    fun isFieldEmpty(field: String): Boolean {
        return field.trim().isEmpty()
    }

    fun validateFields(vararg fields: Pair<String, String>): Boolean {
        fields.forEach { field ->
            if (isFieldEmpty(field.first)) {
                return false
            }
        }
        return true
    }
     fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}