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

    lateinit var preferredCurrency: String

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
//        val userId = extras?.getInt("KEY_ID") ?: -1
//        val name = extras?.getString("KEY_NAME") ?: ""
//        val email = extras?.getString("KEY_EMAIL") ?: ""
//        val phone = extras?.getLong("KEY_PHONE") ?: 0L
//        val password = extras?.getString("KEY_PASSWORD") ?: ""
//        val preferredCurrency = extras?.getString("KEY_CURRENCY") ?: "RSD"
        preferredCurrency = extras?.getString("KEY_CURRENCY") ?: "RSD"
        initComponents()

    }

    private fun  initComponents() {
        val currencies = listOf<String>("RSD", "EUR", "USD")
        val fragmentCall = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as? CallsFragment
        val fragmentService = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as? ServicesFragment

        lifecycleScope.launchWhenStarted {
            val api = ApiRepository()
            for(currency in currencies){
                val result = api.getCurrency("https://api.frankfurter.dev/v2/rate/${currency}/${preferredCurrency}")
                val cur = result.fold(onSuccess = {it}, onFailure = { CurrencyModel(1.0, currency)})

                if(db.getCurrencyByName(currency) == -5.0){
                    db.addCurrency(currency, cur.rate)
                }
                else {
                    db.editCurrency(db.getCurrencyIdByName(currency), currency, cur.rate)
                }
            }
            fragmentCall?.updateAdapter()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_call -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,CallsFragment()).commit()
                    fragmentCall?.updateAdapter()
                    true}
                R.id.nav_service -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,ServicesFragment()).commit()
                    true}
                R.id.nav_advert -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,AdvertFragment()).commit()
                    true}
                R.id.nav_account -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,AccountFragment()).commit()
                    true}
                else -> false
            }
        }
    }

    override fun onClick(v: View?) {

    }

}