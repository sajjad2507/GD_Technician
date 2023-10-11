package com.example.dgtechnician.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.DataModel.RequestAppointmentModel
import com.example.dgtechnician.DataModel.UserProfileModel
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class Profile : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.home.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_home2)

        }

        binding.appointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_bookedAppointments)

        }

        binding.requestedAppointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_requestedAppointment)

        }

//        binding.search.setOnClickListener {
//
//            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_search)
//
//        }

        binding.logOut.setOnClickListener {

            auth.signOut()
            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_splash)

        }

        return binding.root
    }


}