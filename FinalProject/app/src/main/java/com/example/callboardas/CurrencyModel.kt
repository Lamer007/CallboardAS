package com.example.callboardas

import org.json.JSONObject

data class CurrencyModel(val rate: Double, val currency: String) {
    companion object{
        fun fromJson(json: String): CurrencyModel{
            val obj = JSONObject(json)
            val rate = obj.getString("rate").toDouble()
            val currency = obj.getString("quote")
            return CurrencyModel(rate, currency)
        }
    }

}
