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
import com.example.callboardas.databinding.FragmentViewCallBinding
import kotlin.math.round
import kotlin.text.matches
import kotlin.text.toLong

class ViewCallFragment : Fragment() {
    private var _binding: FragmentViewCallBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenCall(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentViewCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun  initComponents() {
        val mainActivity = activity as? MainActivity
        val title = mainActivity?.currentCall?.name
        val description = mainActivity?.currentCall?.description
        val address = mainActivity?.currentCall?.address
        val phone = mainActivity?.currentCall?.phone
        val author = mainActivity?.currentCall?.author
        val price = round(mainActivity?.currentCall?.price?.toDouble()!! * db.getCurrencyByName(mainActivity?.currentCall?.currency.toString())).toInt()

        binding.textViewCTitle.setText(title.toString())
        binding.textViewCDescription.setText(description.toString())
        binding.textViewCAdress.setText("Address: ${address.toString()}")
        binding.textViewCPhone.setText("Phone: ${phone.toString()}")
        binding.textViewAuthor.setText("Author: ${author.toString()}")
        binding.textViewCPrice.setText("${price.toString()} ${mainActivity?.currentUser?.currency}")
    }
}