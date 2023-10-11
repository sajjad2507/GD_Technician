package com.example.dgtechnician.adapters


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtechnician.DataModel.RequestAppointmentModel
import com.example.dgtechnician.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RequestedAppointmentAdapter(
    val requireContext: Context,
    val requestedAppointment: ArrayList<RequestAppointmentModel>,
    var db: FirebaseFirestore,
    val requireParentFragment: Fragment,
) :
    RecyclerView.Adapter<RequestedAppointmentAdapter.RequestedAppointmentHolder>() {

    inner class RequestedAppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val techImage = itemView.findViewById<ImageView>(R.id.techImage)
        val techName = itemView.findViewById<TextView>(R.id.techName)
        val techPhone = itemView.findViewById<TextView>(R.id.techPhone)
        val orderDescription = itemView.findViewById<TextView>(R.id.orderDescription)
        val orderDate = itemView.findViewById<TextView>(R.id.orderDate)
        val actionBtn = itemView.findViewById<Button>(R.id.actionBtn)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestedAppointmentAdapter.RequestedAppointmentHolder {
        return RequestedAppointmentHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_appointment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestedAppointmentHolder, position: Int) {

        val appointment = requestedAppointment[position]

        holder.techName.text = appointment.name
        holder.techPhone.text = appointment.phone
        holder.orderDescription.text = appointment.description
        holder.orderDate.text = appointment.date

        val appointmentDate = appointment.date
        val appointmentTime = appointment.slot
        val currentDateTimeMillis = System.currentTimeMillis()

        val id =
            requestedAppointment[position].slot!!.trim() + "_" + requestedAppointment[position].date.trim()


        // Check if the appointment date and time are in the past
        if (isPastAppointment(appointmentDate, appointmentTime, currentDateTimeMillis)) {

            if (appointment.appointment == "Reviewed") {

                // Set the text of the "Cancel" button to "Review"
                holder.actionBtn.text = "Reviewed"

            } else {
                // Set the text of the "Cancel" button to "Review"
                holder.actionBtn.text = "Review"
            }
        } else {
            // Set the text of the "Cancel" button to "Cancel"
            holder.actionBtn.text = "Cancel"
        }

        holder.actionBtn.setOnClickListener {
            if (holder.actionBtn.text == "Review") {
                // Handle the "Review" button click action
                Toast.makeText(requireContext, "Review", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("techId", appointment.wId)
                bundle.putString("clientName", appointment.name)
                bundle.putString("clientAddress", appointment.address)
                bundle.putString("clientImage", "")
                bundle.putString("id", id)
                NavHostFragment.findNavController(requireParentFragment)
                    .navigate(R.id.action_requestedAppointment_to_reviewScreen, bundle)
                // Add your code to navigate to the review screen
            } else {

                db = FirebaseFirestore.getInstance()

                val wId = appointment.wId

                if (!wId.isEmpty()) {
                    db.collection("workers").document(wId).collection("requestedAppointment")
                        .document(id).update("status", "cancled").addOnSuccessListener {
                            Toast.makeText(requireContext, "Cancel", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext, "failed", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return requestedAppointment.size
    }

    // Function to check if the appointment date and time are in the past
    private fun isPastAppointment(
        appointmentDate: String,
        appointmentTime: String,
        currentDateTimeMillis: Long
    ): Boolean {
        val dateFormat = SimpleDateFormat("dd_MM_yyyy", Locale.getDefault())
//        val dateFormat = SimpleDateFormat("dd_MM_yyyy hh:mm a", Locale.getDefault())
        val dateTimeString = "$appointmentDate"
//        val dateTimeString = "$appointmentDate $appointmentTime"
        try {
            val appointmentDateTime = dateFormat.parse(dateTimeString)
            return currentDateTimeMillis > (appointmentDateTime?.time ?: 0)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }
}

