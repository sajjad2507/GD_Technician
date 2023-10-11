package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dgtechnician.DataModel.TechCategoryModel
import com.example.dgtechnician.R
import com.example.dgtechnician.adapters.TechCategoryAdapter
import com.example.dgtechnician.databinding.FragmentAllCategoriesBinding
import com.google.firebase.firestore.FirebaseFirestore

class AllCategories : Fragment() {

    lateinit var binding: FragmentAllCategoriesBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllCategoriesBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()

        db.collection("techNetwork").addSnapshotListener { value, error ->

            val techCategoryList = arrayListOf<TechCategoryModel>()
            val data = value?.toObjects(TechCategoryModel::class.java)
            techCategoryList.addAll(data!!)

            binding.availableCatgoriesRcv.layoutManager =
                GridLayoutManager(requireContext(), 3)
            binding.availableCatgoriesRcv.adapter =
                TechCategoryAdapter(requireContext(), techCategoryList, this)

        }


        return binding.root
    }
}