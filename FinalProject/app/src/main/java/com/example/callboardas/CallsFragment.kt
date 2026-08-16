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
import com.example.callboardas.databinding.FragmentCallsBinding
import kotlin.lazy

class CallsFragment : Fragment() {

    private var _binding: FragmentCallsBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var callAdapter: CallAdapter
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenCall(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCallsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity
        val prefereCurn = mainActivity?.currentUser?.currency

        recyclerView = binding.recyclerViewCalls
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchView = binding.searchViewCalls
        callAdapter = CallAdapter(db.getAllCalls(), requireContext(), prefereCurn.toString())

        updateAdapter()
        setupSearch(prefereCurn.toString())

        callAdapter.onItemClick = {
            val mainActivity = activity as? MainActivity
            mainActivity?.currentCall = it
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, ViewCallFragment()).commit()
        }
    }
    fun updateAdapter() {
        recyclerView.adapter = callAdapter
    }

    private fun setupSearch(prefereCurn: String) {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterCalls(query, prefereCurn)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterCalls(newText, prefereCurn)
                    return true
                }
            }
        )
    }

    private fun filterCalls(query: String?, prefereCurn: String) {
        if (query.isNullOrBlank()) {
            callAdapter = CallAdapter(db.getAllCalls(), requireContext(), prefereCurn)
            updateAdapter()
            return
        }

        val filteredCalls = db.getAllCalls().filter { call ->
            call.name.contains(query, ignoreCase = true)
        }

        callAdapter = CallAdapter(filteredCalls, requireContext(), prefereCurn)
        updateAdapter()
    }
}