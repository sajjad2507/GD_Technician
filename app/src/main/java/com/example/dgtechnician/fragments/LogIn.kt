package com.example.dgtechnician.fragments

import android.accounts.AccountManager
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentLogInBinding
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogIn : Fragment() {

    lateinit var binding: FragmentLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)


        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.loginBtn.setOnClickListener {

            Toast.makeText(requireContext(), "logIn Btn", Toast.LENGTH_SHORT).show()
            val sEmail = binding.emailEdt.text.toString().trim()
            val sPass = binding.passwordEdt.text.toString().trim()

            auth.signInWithEmailAndPassword(sEmail, sPass)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val verification = auth.currentUser?.isEmailVerified
                        if (verification == true) {
                            goToHome()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please verify your email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }

        binding.dontRegister.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_logIn_to_register)

        }

        binding.googleBtn.setOnClickListener {

            signInGoogle()

        }

        return binding.root
    }

    private fun signInGoogle() {
        val accountManager = AccountManager.get(requireContext())
        val googleAccounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)

        if (googleAccounts.isNotEmpty()) {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        } else {
            Toast.makeText(requireContext(), "No Google accounts found", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            } else {
                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            Toast.makeText(requireContext(), account.email, Toast.LENGTH_SHORT).show()
            if (it.isSuccessful) {
                goToHome()
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {

            goToHome()

        }
    }

    private fun goToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_logIn_to_guide1)
    }
}