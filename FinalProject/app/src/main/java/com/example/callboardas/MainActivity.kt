package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.callboardas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding // creating binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setting up binding
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        val facultyList = listOf("BB", "AA", "CC")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, facultyList)
        //binding.spinnerFaculty.adapter = adapter // !implement spinnerFaculty

        //binding.buttonSend.setOnClickListener(this) //!implement buttonSend button
    }

    override fun onClick(v: View?) {
        /*
        if(v?.id == binding.buttonSend.id) { //!implement buttonSend button
                val intent = Intent(this, ConfirmationActivity::class.java) //Implement ConfirmatioonActivity
                val name = binding.editTextName.text.toString()
                // other views valls

                val extras = Bundle().apply{
                    putString("KEY_NAME", name)
                    // other extras
                }

                intent.putExtras(extras)
                startActivity(intent)
            }
        }

         */
        println("Yeah")
    }

}