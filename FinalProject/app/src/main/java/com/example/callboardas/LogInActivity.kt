package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callboardas.databinding.ActivityLogInBinding


class LogInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLogInBinding
    private val db: DataBase by lazy { DataBase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
    }

    fun addSomeAdvertsUsers() {
        db.addUser("alex", "alex@gmail.com", 381621234567, "qwerty123", "RSD")
        db.addCall("test1", "description1", "obalskih radnika", "alex", 1, 123456789, 1234, "RSD")
        db.addCall("test2", "description2", "radovana simice cige", "alex", 1, 123456789, 34, "EUR")
        db.addCall("test3", "description3", "danijelova", "alex", 1, 123456789, 12, "USD")
        db.addService("test12", "description12", "kumodrashka", "alex", 1, 123456789, 4321, "RSD")
        db.addService("test22", "description22", "ustanichka", "alex", 1, 123456789, 43, "EUR")
        db.addService("test32", "description32", "dositejeva", "alex", 1, 123456789, 21, "USD")
        db.addCurrency("EUR", 117.0)
        db.addCurrency("USD", 100.0)
        db.addCurrency("RSD", 1.0)
    }
    private fun  initComponents() {
        addSomeAdvertsUsers()
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

        val email = binding.editTexLogInEmail.text.toString()
        val password = binding.editTextLogInPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this@LogInActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())) {
            Toast.makeText(this@LogInActivity, "Please enter your email correctly example: test@gmail.com", Toast.LENGTH_SHORT).show()
            return
        }

        var user: UserModel?

        if(db.getUserIdByEmail(email) < 0){
            Toast.makeText(this@LogInActivity, "The email is not registered", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            user = db.getUserById(db.getUserIdByEmail(email))

            if(user?.password != password) {
                Toast.makeText(this@LogInActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                return
            }
        }


        val extras = Bundle().apply {
            putInt("KEY_ID", user.userId)
            putString("KEY_NAME", user.name)
            putString("KEY_EMAIL", user.email)
            putLong("KEY_PHONE", user.phone)
            putString("KEY_PASSWORD", user.password)
            putString("KEY_CURRENCY", user.currency)
        }

        intent.putExtras(extras)
        startActivity(intent)
    }

    fun doRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}