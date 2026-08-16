package com.example.callboardas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callboardas.databinding.FragmentAdvertBinding

class AdvertFragment : Fragment() {
    private var _binding: FragmentAdvertBinding? = null
    private val binding get() = _binding!!
    private var mListener: OnFragmentInteractionListener? = null
    private val db: DataBase by lazy { DataBase(requireContext()) }

    interface OnFragmentInteractionListener {
        fun theChosenCall(callId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAdvertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun  initComponents() {
        val mainActivity = activity as? MainActivity
        val currency = mainActivity?.currentUser?.currency

        binding.textViewAdvertPrice.setText("Price in ${currency}")

        binding.buttonCreate.setOnClickListener { view -> when (view?.id) {
            R.id.buttonCreate -> {makeAdvert()}
            }
        }
    }

    fun makeAdvert() {
        val mainActivity = activity as? MainActivity
        val author = mainActivity?.currentUser?.name
        val authorId = mainActivity?.currentUser?.userId
        val phone = mainActivity?.currentUser?.phone
        val currency = mainActivity?.currentUser?.currency

         val advertType = when(binding.radioGroupAdvertType.checkedRadioButtonId) {
         binding.radioButtonCall.id -> binding.radioButtonCall.text.toString()
         binding.radioButtonService.id -> binding.radioButtonService.text.toString()
         else -> ""}

        val title = binding.editTextAdvertTitle.text.toString()
        val description = binding.editTextAdvertDescription.text.toString()
        val address = binding.editTextAdvertAddress.text.toString()
        val price = binding.editTextAdvertPrice.text.toString()

        if(advertType.isEmpty() || title.isEmpty() || description.isEmpty() || address.isEmpty() || price.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            if(advertType == "Call") {
                db.addCall(title, description, address, author.toString(), authorId!!.toInt(), phone!!.toLong(), price.toInt(), currency.toString())
                Toast.makeText(requireContext(), "Call is created", Toast.LENGTH_SHORT).show()
                clearAdvert()
                return
            }

            if(advertType == "Service") {
                db.addService(title, description, address, author.toString(), authorId!!.toInt(), phone!!.toLong(), price.toInt(), currency.toString())
                Toast.makeText(requireContext(), "Service is created", Toast.LENGTH_SHORT).show()
                clearAdvert()
                return
            }
        }
    }

    fun clearAdvert() {
        binding.radioGroupAdvertType.clearCheck()
        binding.editTextAdvertTitle.text.clear()
        binding.editTextAdvertDescription.text.clear()
        binding.editTextAdvertAddress.text.clear()
        binding.editTextAdvertPrice.text.clear()
    }
}