package com.example.callboardas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callboardas.databinding.FragmentServicesBinding
import kotlin.lazy
import kotlin.toString

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var serviceAdapter: ServiceAdapter
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenService(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity
        val prefereCurn = mainActivity?.currentUser?.currency

        recyclerView = binding.recyclerViewServices
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchView = binding.searchViewServices
        serviceAdapter = ServiceAdapter(db.getAllServices(), requireContext(), prefereCurn.toString())

        updateAdapter()
        setupSearch(prefereCurn.toString())

        serviceAdapter.onItemClick = {
            val mainActivity = activity as? MainActivity
            mainActivity?.currentService = it
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, ViewServiceFragment()).commit()
        }
    }
    fun updateAdapter() {
        recyclerView.adapter = serviceAdapter
    }

    private fun setupSearch(prefereCurn: String) {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterServices(query, prefereCurn)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterServices(newText, prefereCurn)
                    return true
                }
            }
        )
    }

    private fun filterServices(query: String?, prefereCurn: String) {
        if (query.isNullOrBlank()) {
            serviceAdapter = ServiceAdapter(db.getAllServices(), requireContext(), prefereCurn)
            updateAdapter()
            return
        }

        val filteredCalls = db.getAllServices().filter { call ->
            call.name.contains(query, ignoreCase = true)
        }

        serviceAdapter = ServiceAdapter(filteredCalls, requireContext(), prefereCurn)
        updateAdapter()
    }
}