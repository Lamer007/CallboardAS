package com.example.callboardas

import android.icu.util.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class ApiRepository {
    suspend fun getCurrency(url: String): Result<CurrencyModel> = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val response = connection.inputStream.bufferedReader().use(BufferedReader::readText)
            connection.disconnect()

            val currency = CurrencyModel.fromJson(response)
            Result.success(currency)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}