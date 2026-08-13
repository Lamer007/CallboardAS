package com.example.callboardas

data class CallModel(val callId: Int, val name: String, val description: String, val author: String,
    val phone: String, val price: Int) {
    companion object {
        const val TABLE_NAME = "call"
        const val COLUMN_CALL_ID = "call_id"
        const val COLUMN_CALL_NAME = "name"
        const val COLUMN_CALL_DESCRIPTION = "description"
        const val COLUMN_CALL_AUTHOR = "author"
        const val COLUMN_CALL_PHONE = "phone"
        const val COLUMN_CALL_PRICE = "price"
    }

    override fun toString(): String {
        return "CallModel(call_id = $callId, name = '$name', description = '$description', author = '$author', phone = '$phone', price = $price)"
    }
}
