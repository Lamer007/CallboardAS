package com.example.callboardas

import org.json.JSONObject

data class CurrencyModel(val rate: Double, val currency: String) {
    companion object{
        const val TABLE_NAME = "currency"
        const val COLUMN_CURRENCY_ID = "id"
        const val COLUMN_CURRENCY_NAME = "name"
        const val COLUMN_CURRENCY_RATE = "rate"

        fun fromJson(json: String): CurrencyModel{
            val obj = JSONObject(json)
            val rate = obj.getString("rate").toDouble()
            val currency = obj.getString("quote")
            return CurrencyModel(rate, currency)
        }
    }

}
