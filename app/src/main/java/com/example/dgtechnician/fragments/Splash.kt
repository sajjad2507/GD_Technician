package com.example.dgtechnician.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Splash : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({

            if (currentUser != null) {
                NavHostFragment.findNavController(this).navigate(R.id.action_splash_to_home22)

            } else {

                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_splash_to_getStarted)

            }

        }, 3000)

        return binding.root
    }
}