package com.example.callboardas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CallsFragment : Fragment() {

    private var _binding: CallsFragment? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        // to do fun
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calls, container, false)
    }

    companion object {

    }
}