package com.example.callboardas

data class CallModel(val callId: Int, val name: String, val description: String, val address: String, val author: String, val authorId: Int,
    val phone: Int, val price: Int, val currency: String) {
    companion object {
        const val TABLE_NAME = "call"
        const val COLUMN_CALL_ID = "call_id"
        const val COLUMN_CALL_NAME = "name"
        const val COLUMN_CALL_DESCRIPTION = "description"
        const val COLUMN_CALL_ADDRESS = "address"
        const val COLUMN_CALL_AUTHOR = "author"
        const val COLUMN_CALL_AUTHOR_ID = "author_id"
        const val COLUMN_CALL_PHONE = "phone"
        const val COLUMN_CALL_PRICE = "price"
        const val COLUMN_CALL_CURRENCY = "currency"
    }

    override fun toString(): String {
        return "CallModel(call_id = $callId, name = '$name', description = '$description', address = '$address', author = '$author', author_id = $authorId, phone = $phone, price = $price, currency = '$currency')"
    }
}
