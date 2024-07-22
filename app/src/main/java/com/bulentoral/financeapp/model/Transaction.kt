package com.bulentoral.financeapp.model

data class Transaction(
    var transactionId: String? = null,
    var senderUid: String? = null,
    var senderAccountId: String? = null,
    var receiverIban: String? = null,
    var amount: Double? = null,
    var note: String? = null,
    var timestamp: Long? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "transactionId" to transactionId,
            "senderUid" to senderUid,
            "senderAccountId" to senderAccountId,
            "receiverIban" to receiverIban,
            "amount" to amount,
            "note" to note,
            "timestamp" to timestamp
        )
    }
}