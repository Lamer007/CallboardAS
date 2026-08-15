package com.example.callboardas

data class UserModel(val userId: Int, val name: String, val email: String, val phone: Int, val password: String,
                     val currency: String) {
    companion object {
        const val TABLE_NAME = "user"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PHONE = "phone"
        const val COLUMN_USER_PASSWORD = "password"
        const val COLUMN_USER_CURRENCY = "currency"
    }

    override fun toString(): String {
        return "UserModel(user_id = $userId, name = '$name', email = '$email', phone = '$phone', password = '$password', currency = '$currency')"
    }
}
