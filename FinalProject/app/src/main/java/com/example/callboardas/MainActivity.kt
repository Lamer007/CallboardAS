package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.callboardas.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlin.getValue

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val db: DataBase by lazy { DataBase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val extras = intent.extras
        val userId = extras?.getInt("KEY_ID") ?: -1
        val name = extras?.getString("KEY_NAME") ?: ""
        val email = extras?.getString("KEY_EMAIL") ?: ""
        val phone = extras?.getLong("KEY_PHONE") ?: 0L
        val password = extras?.getString("KEY_PASSWORD") ?: ""
        val preferredCurrency = extras?.getString("KEY_CURRENCY") ?: "RSD"
        initComponents(preferredCurrency)

    }

    private fun  initComponents(preferredCurrency: String) {
        val currencies = listOf<String>("RSD", "EUR", "USD")
        var rate = mutableMapOf<String, Double>()

        lifecycleScope.launchWhenStarted {
            val api = ApiRepository()
            for(currency in currencies){
                val result = api.getCurrency("https://api.frankfurter.dev/v2/rate/${preferredCurrency}/${currency}")
                val cur = result.fold(onSuccess = {it}, onFailure = { CurrencyModel(1.0, currency)})
                rate[cur.currency] = cur.rate
            }
            
        }
    }

    override fun onClick(v: View?) {
        println("Yeah")
    }

}