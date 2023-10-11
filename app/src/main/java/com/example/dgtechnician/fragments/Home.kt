package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnician.DataModel.AvailableTechnicianModel
import com.example.dgtechnician.DataModel.BestTechModel
import com.example.dgtechnician.DataModel.RequestAppointmentModel
import com.example.dgtechnician.DataModel.TechCategoryModel
import com.example.dgtechnician.MainActivity
import com.example.dgtechnician.R
import com.example.dgtechnician.adapters.BestTechAdapter
import com.example.dgtechnician.adapters.RequestedAppointmentAdapter
import com.example.dgtechnician.adapters.TechCategoryAdapter
import com.example.dgtechnician.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
//        // Show the status bar
//        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.homeNotification.setOnClickListener {
//                auth.signOut()
            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_requestedAppointment)
        }

        db.collection("bestTechnicians").addSnapshotListener { value, error ->

            val bestTechList = arrayListOf<BestTechModel>()
            val data = value?.toObjects(BestTechModel::class.java)
            bestTechList.addAll(data!!)

            val bestTechAdapter = BestTechAdapter(requireContext(), bestTechList)

            binding.bestTechRcv.adapter = bestTechAdapter
            binding.bestTechRcv.apply {
//                set3DItem(true)
                setAlpha(true)
                setInfinite(true)
            }

        }

        db.collection("techNetwork").addSnapshotListener { value, error ->

            val techCategoryList = arrayListOf<TechCategoryModel>()
            val data = value?.toObjects(TechCategoryModel::class.java)
            techCategoryList.addAll(data!!)

            binding.techCatRcv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.techCatRcv.adapter =
                TechCategoryAdapter(requireContext(), techCategoryList, this)

        }

        db.collection("users").document(auth.uid!!).collection("requestedAppointment")
            .addSnapshotListener { value, error ->

                val requestedAppointment = arrayListOf<RequestAppointmentModel>()
                val filteredList = arrayListOf<RequestAppointmentModel>()
                val data = value?.toObjects(RequestAppointmentModel::class.java)
                requestedAppointment.addAll(data!!)

                for (i in requestedAppointment) {
                    if (i.status == "booked") {
                        filteredList.add(i)
                    }
                }

                binding.appointmentRcv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.appointmentRcv.adapter =
                    RequestedAppointmentAdapter(
                        requireContext(),
                        filteredList, db, requireParentFragment()
                    )

            }

        binding.homeSeeAll.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_allCategories)

        }


//        db.collection("workers").orderBy("timestamp", Query.Direction.DESCENDING)
//            .addSnapshotListener { value, error ->
//
//                if (error != null) {
//                    // Handle error
//                    return@addSnapshotListener
//                }
//
//                val catList = arrayListOf<AvailableTechnicianModel>()
//
//                // Process the updated data
//                val data = value?.toObjects(AvailableTechnicianModel::class.java)
//                catList.addAll(data!!)
//
//            }


        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                        return
                    } else {
                        Toast.makeText(
                            requireContext(),
                            doubleBackToExitPressedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })

        binding.menuSearch.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_search)

        }

        binding.menuAppointments.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_bookedAppointments)

        }

        binding.menuProfile.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_home2_to_profile)

        }

        return binding.root
    }
}