package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnician.DataModel.RequestAppointmentModel
import com.example.dgtechnician.adapters.RequestedAppointmentAdapter
import com.example.dgtechnician.databinding.FragmentRequestedAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RequestedAppointment : Fragment() {

    lateinit var binding: FragmentRequestedAppointmentBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestedAppointmentBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document(userId).collection("requestedAppointment")
            .addSnapshotListener { value, error ->

                val requestedAppointment = arrayListOf<RequestAppointmentModel>()
                val filteredList = arrayListOf<RequestAppointmentModel>()
                val data = value?.toObjects(RequestAppointmentModel::class.java)
                requestedAppointment.addAll(data!!)

                for (i in requestedAppointment) {
                    if (i.status == "request") {
                        filteredList.add(i)
                    }
                }

                binding.requestedAppointmentRcv.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.requestedAppointmentRcv.adapter =
                    RequestedAppointmentAdapter(
                        requireContext(),
                        filteredList, db, requireParentFragment()
                    )

            }

        return binding.root
    }
}