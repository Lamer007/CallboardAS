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
import com.example.callboardas.databinding.FragmentViewServiceBinding
import kotlin.math.round
import kotlin.text.matches
import kotlin.text.toLong

class ViewServiceFragment : Fragment() {
    private var _binding: FragmentViewServiceBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenCall(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentViewServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun  initComponents() {
        val mainActivity = activity as? MainActivity
        val title = mainActivity?.currentService?.name
        val description = mainActivity?.currentService?.description
        val address = mainActivity?.currentService?.address
        val phone = mainActivity?.currentService?.phone
        val author = mainActivity?.currentService?.author
        val price = round(mainActivity?.currentService?.price?.toDouble()!! * db.getCurrencyByName(mainActivity?.currentService?.currency.toString())).toInt()

        binding.textViewSTitle.setText(title.toString())
        binding.textViewSDescription.setText(description.toString())
        binding.textViewSAddress.setText("Address: ${address.toString()}")
        binding.textViewSPhone.setText("Phone: ${phone.toString()}")
        binding.textViewSAuthor.setText("Author: ${author.toString()}")
        binding.textViewSPrice.setText("${price.toString()} ${mainActivity?.currentUser?.currency}")
    }
}