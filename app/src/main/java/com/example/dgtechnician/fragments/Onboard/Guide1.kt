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
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentGuide1Binding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Guide1 : Fragment() {

    lateinit var binding: FragmentGuide1Binding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuide1Binding.inflate(layoutInflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.nextBtn.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_guide1_to_guide2)

        }

        binding.btnSkip.setOnClickListener {

            editor.putString("value", "checked")
            editor.apply()

            val navController = NavHostFragment.findNavController(this)

            navController.navigate(R.id.action_guide1_to_home2)

        }

        return binding.root
    }

}