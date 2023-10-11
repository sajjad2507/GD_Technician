package com.example.dgtechnician.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentTechProfileBinding

class TechProfile : Fragment() {

    lateinit var binding: FragmentTechProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTechProfileBinding.inflate(layoutInflater, container, false)

        val catId = arguments?.getString("myStringArg")


        binding.bookAppointmentBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("techId", catId)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_techProfile_to_makeAppointment, bundle)

        }

        binding.messageBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("techId", catId)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_techProfile_to_chat, bundle)

        }

        binding.callBtn.setOnClickListener {
            val phoneNumber = "03343727250"
            openDialerWithNumber(phoneNumber)
        }

        return binding.root
    }

    private fun openDialerWithNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")

            startActivity(intent)

    }

}