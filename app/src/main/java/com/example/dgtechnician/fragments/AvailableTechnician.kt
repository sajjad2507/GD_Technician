package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dgtechnician.DataModel.AvailableTechnicianModel
import com.example.dgtechnician.adapters.AvailableTechAdapter
import com.example.dgtechnician.databinding.FragmentAvailableTechnicianBinding
import com.google.firebase.firestore.FirebaseFirestore

class AvailableTechnician : Fragment() {

    lateinit var binding: FragmentAvailableTechnicianBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableTechnicianBinding.inflate(layoutInflater, container, false)

        val catName = arguments?.getString("catName")

        db = FirebaseFirestore.getInstance()

        db.collection("techNetwork").document(catName!!).collection("technicains")
            .addSnapshotListener { value, error ->

                val availableTechList = arrayListOf<AvailableTechnicianModel>()
                val data = value?.toObjects(AvailableTechnicianModel::class.java)
                availableTechList.addAll(data!!)

                binding.availableTechRcv.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.availableTechRcv.adapter =
                    AvailableTechAdapter(
                        requireContext(),
                        availableTechList,
                        catName!!,
                        requireParentFragment()
                    )
            }

        return binding.root
    }
}