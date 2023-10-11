package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dgtechnician.DataModel.AppointmentCheckModel
import com.example.dgtechnician.DataModel.RequestAppointmentModel
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentConfirmAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmAppointment : Fragment() {

    lateinit var binding: FragmentConfirmAppointmentBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConfirmAppointmentBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()

        val date = arguments?.getString("date", "")
        val workerId = arguments?.getString("workerId", "")
        val slotId = arguments?.getString("slotId", "")

        binding.confirmAppoitmentBtn.setOnClickListener {

            if (binding.nameEdt.text.isEmpty() || binding.phoneEdt.text.isEmpty() || binding.emailEdt.text.isEmpty() || binding.addressEdt.text.isEmpty()) {

                Toast.makeText(requireContext(), "Fill the complete details", Toast.LENGTH_SHORT)
                    .show()

            } else {

                val uName = binding.nameEdt.text.toString()
                val uPhone = binding.phoneEdt.text.toString()
                val uEmail = binding.emailEdt.text.toString()
                val uAddress = binding.addressEdt.text.toString()
                val uDescription = binding.descriptionEdt.text.toString()

                val id = slotId!!.trim() + "_" + date!!.trim()
                val userId = FirebaseAuth.getInstance().currentUser!!.uid

                val slotData = AppointmentCheckModel(

                    slot = slotId,
                    status = "booked"

                )

                val data = RequestAppointmentModel(

                    name = uName,
                    phone = uPhone,
                    email = uEmail,
                    address = uAddress,
                    description = uDescription,
                    status = "request",
                    uId = userId,
                    date = date,
                    slot = slotId,
                    wId = workerId.toString().trim()

                )

                db.collection("workers").document(workerId.toString().trim())
                    .collection("requestedAppointment")
                    .document(id).set(data).addOnSuccessListener {

                        db.collection("users").document(userId)
                            .collection("requestedAppointment")
                            .document(id).set(data).addOnSuccessListener {

                                db.collection("workers").document(workerId!!)
                                    .collection("appointment").document(date).collection("appointments")
                                    .document(slotId).set(slotData).addOnSuccessListener {

                                        Toast.makeText(
                                            requireContext(),
                                            "Request Submitted Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                        NavHostFragment.findNavController(this).navigate(R.id.action_confirmAppointment_to_requestedAppointment)

                                    }

                            }

                    }


            }

        }

        return binding.root
    }
}