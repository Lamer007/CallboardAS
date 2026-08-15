package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callboardas.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
    }

    private fun  initComponents() {
        val currencyList = listOf("RSD", "EUR", "USD")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        binding.spinnerCurrency.adapter = adapter

        binding.buttonRegister.setOnClickListener(this)
        binding.buttonBackLogIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.buttonRegister.id -> doRegister()
            binding.buttonBackLogIn.id -> doBackLogIn()
        }
    }

    fun doRegister() {
        val intent = Intent(this, MainActivity::class.java)

        val name = binding.editTextRegistrationName.text.toString()
        val email = binding.editTextRegistrationEmail.text.toString()
        val phone = binding.editTextRegistrationPhone.text.toString()
        val password = binding.editTextRegistrationPassword.text.toString()
        val currency = binding.spinnerCurrency.selectedItem.toString()

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || currency.isEmpty()){
            Toast.makeText(this@RegistrationActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())) {
            Toast.makeText(this@RegistrationActivity, "Please enter your email correctly example: test@gmail.com", Toast.LENGTH_SHORT).show()
            return
        }

        if(!phone.matches("^0\\d{9}$".toRegex())) {
            Toast.makeText(this@RegistrationActivity, "Please enter your phone correctly example: 0XXXXXXXXX", Toast.LENGTH_SHORT).show()
            return
        }

        if(!password.matches("^.{8,}$".toRegex())) {
            Toast.makeText(this@RegistrationActivity, "Please make your password 8 or more characters long", Toast.LENGTH_SHORT).show()
            return
        }

        //if() {
        //    Toast.makeText(this@RegistrationActivity, "Email address already registered", Toast.LENGTH_SHORT).show()
        //    return
        //}

        val extras = Bundle().apply {
            putString("KEY_NAME", name)
            putString("KEY_EMAIL", email)
            putInt("KEY_PHONE", phone.toInt())
            putString("KEY_PASSWORD", password)
            putString("KEY_CURRENCY", currency)
        }

        intent.putExtras(extras)
        startActivity(intent)
    }

    fun doBackLogIn() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }
}