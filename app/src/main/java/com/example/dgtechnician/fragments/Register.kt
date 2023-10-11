package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.registerBtn.setOnClickListener {

            val eMail = binding.emailEdt.text.toString().trim()
            val password = binding.passwordEdt.text.toString().trim()
            val confirmPassword = binding.confirmPassEdt.text.toString().trim()

            if (password == confirmPassword) {

                auth.createUserWithEmailAndPassword(eMail, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                requireContext(), "Please verify your email", Toast.LENGTH_SHORT
                            ).show()
                            auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Authentication successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }?.addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Authentication failed!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            goToLogIn()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                requireContext(), "Authentication failed.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {

                Toast.makeText(requireContext(), "Password Does not Match", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        binding.dontSignIn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_register_to_logIn)

        }

        return binding.root
    }

    private fun goToLogIn() {
        NavHostFragment.findNavController(this).navigate(R.id.action_register_to_logIn)
    }

}