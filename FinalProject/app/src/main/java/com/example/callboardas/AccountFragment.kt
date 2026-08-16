package com.example.callboardas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callboardas.databinding.FragmentAccountBinding
import kotlin.text.matches
import kotlin.text.toLong

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenCall(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun  initComponents() {
        val mainActivity = activity as? MainActivity
        val userId = mainActivity?.currentUser?.userId
        val name = mainActivity?.currentUser?.name
        val email = mainActivity?.currentUser?.email
        val phone = mainActivity?.currentUser?.phone
        val password = mainActivity?.currentUser?.password
        val currency = mainActivity?.currentUser?.currency

        val currencyList = listOf("RSD", "EUR", "USD")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyList)
        binding.spinnerCurrentCurrency.adapter = adapter
        binding.spinnerCurrentCurrency.setSelection(currencyList.indexOf(currency.toString()))

        binding.editTextCurrentName.setText(name.toString())
        binding.editTextCurrentEmail.setText(email.toString())
        binding.editTextCurrentPhone.setText(phone.toString())
        binding.editTextCurrentPassword.setText(password.toString())

        val saveChangesButton: Button = binding.buttonSaveChanges
        saveChangesButton.setOnClickListener {
            editAccount()
        }

        val exitButton: Button = binding.buttonLogOut
        exitButton.setOnClickListener {
            val intent = Intent(requireContext(), LogInActivity::class.java)
            startActivity(intent)
        }
    }

    fun editAccount() {
        val mainActivity = activity as? MainActivity
        val userId = mainActivity?.currentUser?.userId
        val name = mainActivity?.currentUser?.name
        val email = mainActivity?.currentUser?.email
        val phone = mainActivity?.currentUser?.phone
        val password = mainActivity?.currentUser?.password
        val currency = mainActivity?.currentUser?.currency

        val editedName = binding.editTextCurrentName.text.toString()
        val editedEmail = binding.editTextCurrentEmail.text.toString()
        val editedPhone = binding.editTextCurrentPhone.text.toString()
        val editedPassword = binding.editTextCurrentPassword.text.toString()
        val editedCurrency = binding.spinnerCurrentCurrency.selectedItem.toString()

        if(editedName.isEmpty() || editedEmail.isEmpty() || editedPhone.isEmpty() || editedPassword.isEmpty() || editedCurrency.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if(!editedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())) {
            Toast.makeText(requireContext(), "Please enter your email correctly example: test@gmail.com", Toast.LENGTH_SHORT).show()
            return
        }

        if(!editedPhone.matches("^381\\d{9}$".toRegex())) {
            Toast.makeText(requireContext(), "Please enter your phone correctly example: 381XXXXXXXXX", Toast.LENGTH_SHORT).show()
            return
        }

        if(!editedPassword.matches("^.{8,}$".toRegex())) {
            Toast.makeText(requireContext(), "Please make your password 8 or more characters long", Toast.LENGTH_SHORT).show()
            return
        }

        if(db.getUserIdByEmail(editedEmail) > -1 && email.toString() != editedEmail) {
            Toast.makeText(requireContext(), "Email address already registered", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            db.editUser(userId!!.toInt(), editedName, editedEmail, editedPhone.toLong(), editedPassword, editedCurrency)
            mainActivity.currentUser = UserModel(userId.toInt(), editedName, editedEmail, editedPhone.toLong(), editedPassword, editedCurrency)
            mainActivity.initComponents()
            Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
            return
        }
    }
}