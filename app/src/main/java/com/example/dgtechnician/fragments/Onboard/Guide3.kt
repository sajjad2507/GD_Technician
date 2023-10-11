package com.example.dgtechnician.fragments.Onboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnician.DataModel.BestTechModel
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentGuide3Binding
import com.google.firebase.firestore.FirebaseFirestore

class Guide3 : Fragment() {

    lateinit var binding: FragmentGuide3Binding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuide3Binding.inflate(layoutInflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.nextBtn.setOnClickListener {

            editor.putString("value", "checked")
            editor.apply()

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_guide3_to_home2)

        }

        binding.btnSkip.setOnClickListener {

            editor.putString("value", "checked")
            editor.apply()

            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_guide3_to_home2)

        }

        return binding.root
    }
}