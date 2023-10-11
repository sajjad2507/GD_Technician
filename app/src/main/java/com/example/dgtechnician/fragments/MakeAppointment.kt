package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dgtechnician.DataModel.AppointmentCheckModel
import com.example.dgtechnician.DataModel.SlotModel
import com.example.dgtechnician.R
import com.example.dgtechnician.adapters.SlotAdapter
import com.example.dgtechnician.databinding.FragmentMakeAppointmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MakeAppointment : Fragment(), SlotAdapter.OnItemClickListener {

    lateinit var binding: FragmentMakeAppointmentBinding
    lateinit var db: FirebaseFirestore
    var date: String = ""
    var slot: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakeAppointmentBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()

        val currentDate = Calendar.getInstance()

        if (date == "") {
            val currentDate = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd_MM_yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate.time)
            this.date = formattedDate
        }

        showSlots()

        binding.calendarView.minDate = currentDate.timeInMillis

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd_MM_yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            date = formattedDate.toString()

            showSlots()

            Toast.makeText(requireContext(), "Selected Date: $formattedDate", Toast.LENGTH_SHORT)
                .show()

        }


        val techId = arguments?.getString("techId").toString().trim()

        db.collection("workers").document(techId).collection("slots")
            .addSnapshotListener { value, error ->

                val slotList = arrayListOf<SlotModel>()
                val data = value?.toObjects(SlotModel::class.java)
                slotList.addAll(data!!)


            }

        binding.setAppoitmentBtn.setOnClickListener {

            val dateId = date
            val workerId = arguments?.getString("techId", "")
            val slotId = slot
            val bundle = Bundle()
            bundle.putString("date", dateId)
            bundle.putString("workerId", workerId)
            bundle.putString("slotId", slotId)

            if (slot == "") {

                Toast.makeText(requireContext(), "Please Select An Appointment", Toast.LENGTH_SHORT)
                    .show()

            } else {

                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_makeAppointment_to_confirmAppointment, bundle)

            }
        }

        return binding.root
    }

    private fun showSlots() {

        val techId = arguments?.getString("techId").toString().trim()

        db.collection("workers").document(techId).collection("slots")
            .addSnapshotListener { value, error ->

                val slotList = arrayListOf<SlotModel>()
                val data = value?.toObjects(SlotModel::class.java)
                slotList.addAll(data!!)

                checkSlotAvailability(slotList, date)

            }

    }

    private fun checkSlotAvailability(slotList: ArrayList<SlotModel>, date: String) {

        val techId = arguments?.getString("techId").toString().trim()

        db.collection("workers").document(techId).collection("appointment")
            .document(date).collection("appointments").addSnapshotListener { value, error ->

                val slotList1 = arrayListOf<AppointmentCheckModel>()
                val data = value?.toObjects(AppointmentCheckModel::class.java)
                slotList1.addAll(data!!)

                var slotList2 = arrayListOf<AppointmentCheckModel>()
                var vr = 0

                if (value!!.isEmpty) {

                    for (i in slotList) {

                        val data = AppointmentCheckModel(i.slot, "")
                        slotList2.add(data)

                    }

                } else {

                    var slotIndex = 0
                    while (slotIndex < slotList.size && vr < slotList1.size) {
                        val slot = slotList[slotIndex]
                        val appointment = slotList1[vr]

                        if (slot.slot == appointment.slot) {
                            // Slot is booked
                            val appointmentCheckModel = AppointmentCheckModel(slot.slot, "booked")
                            slotList2.add(appointmentCheckModel)
                            vr++
                        } else {
                            // Slot is not booked
                            val appointmentCheckModel = AppointmentCheckModel(slot.slot, "")
                            slotList2.add(appointmentCheckModel)
                        }

                        slotIndex++
                    }

                    // Add remaining slots as not booked
                    while (slotIndex < slotList.size) {
                        val slot = slotList[slotIndex]
                        val appointmentCheckModel = AppointmentCheckModel(slot.slot, "")
                        slotList2.add(appointmentCheckModel)
                        slotIndex++
                    }
                }

                binding.slotsRcv.layoutManager = GridLayoutManager(requireContext(), 3)
                val slotAdapter = SlotAdapter(requireContext(), slotList2, this)
                slotAdapter.setOnItemClickListener(this)
                binding.slotsRcv.adapter = slotAdapter

            }

    }

    override fun onItemClick(slotModel: AppointmentCheckModel) {

        val techId = arguments?.getString("techId").toString().trim()

        if (slotModel.status == "booked") {

            Toast.makeText(requireContext(), "Already booked", Toast.LENGTH_SHORT).show()
            slot = ""

        } else {

            slot = slotModel.slot.toString()
            Toast.makeText(requireContext(), slotModel.slot, Toast.LENGTH_SHORT).show()

            db.collection("workers").document(techId)
                .collection("appointment").document(date).collection("appointments").document(date)

        }
    }
}