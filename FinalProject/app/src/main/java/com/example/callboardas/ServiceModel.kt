package com.example.callboardas

data class ServiceModel(val serviceId: Int, val name: String, val description: String, val address: String, val author: String, val authorId: Int,
                        val phone: Long, val price: Int, val currency: String) {

    companion object {
        const val TABLE_NAME = "service"
        const val COLUMN_SERVICE_ID = "service_id"
        const val COLUMN_SERVICE_NAME = "name"
        const val COLUMN_SERVICE_DESCRIPTION = "description"
        const val COLUMN_SERVICE_ADDRESS = "address"
        const val COLUMN_SERVICE_AUTHOR = "author"
        const val COLUMN_SERVICE_AUTHOR_ID = "author_id"
        const val COLUMN_SERVICE_PHONE = "phone"
        const val COLUMN_SERVICE_PRICE = "price"
        const val COLUMN_SERVICE_CURRENCY = "currency"
    }

    override fun toString(): String {
        return "ServiceModel(service_id = $serviceId, name = '$name', description = '$description', address = '$address', author = '$author', author_id = $authorId, phone = $phone, price = $price, currency = '$currency')"
    }

}
