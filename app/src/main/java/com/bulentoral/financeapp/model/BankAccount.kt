package com.bulentoral.financeapp.model

import java.math.BigDecimal

data class BankAccount(
    var accountId: String? = null,
    var userId: String? = null,
    var bankName: String? = null,
    var iban: String? = null,
    var accountBalance: Long? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "accountId" to accountId,
            "userId" to userId,
            "bankName" to bankName,
            "iban" to iban,
            "accountBalance" to accountBalance
        )
    }
}
