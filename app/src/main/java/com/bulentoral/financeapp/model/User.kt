package com.bulentoral.financeapp.model

data class User(
    var userId: String? = null,
    var fullName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null, // TODO: Firebase ile kontrolü sağlanacak vakit kalırsa
    //var address: String? = null,

) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "fullName" to fullName,
            "lastName" to lastName,
            "email" to email,
            "phoneNumber" to phoneNumber,
            //"address" to address
        )
    }
}