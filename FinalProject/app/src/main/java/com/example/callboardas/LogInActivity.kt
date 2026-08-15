package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callboardas.databinding.ActivityLogInBinding


class LogInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
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
        binding.buttonLogIn.setOnClickListener(this)
        binding.buttonRegistration.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.buttonLogIn.id -> doLogIn()
            binding.buttonRegistration.id -> doRegistration()
        }
    }

    fun doLogIn() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun doRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}