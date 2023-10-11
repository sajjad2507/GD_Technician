package com.example.dgtechnician.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dgtechnician.DataModel.ClientReviewModel
import com.example.dgtechnician.R
import com.example.dgtechnician.databinding.FragmentReviewScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ReviewScreen : Fragment() {

    lateinit var binding: FragmentReviewScreenBinding
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewScreenBinding.inflate(layoutInflater, container, false)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uId = auth.uid

        val techId = arguments?.getString("techId").toString().trim()

        Toast.makeText(requireContext(), techId, Toast.LENGTH_SHORT).show()

        val clientName = arguments?.getString("clientName").toString().trim()
        val clientAddress = arguments?.getString("clientAddress").toString().trim()
        val clientImage = arguments?.getString("clientImage").toString().trim()
        val id = arguments?.getString("id", "id").toString().trim()

        binding.submitButton.setOnClickListener {

            val clientReview = binding.reviewEditText.text.toString()
            val clientRating: Double = binding.ratingBar.rating.toDouble()

            val currentDate = Date()
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val reviewDate = dateFormat.format(currentDate)

            val documentReference = db.collection("workers").document(techId)

            val data = ClientReviewModel(
                clientName = clientName,
                clientImage = clientImage,
                clientAddress = clientAddress,
                clientRating = clientRating,
                clientReview = clientReview,
                reviewDate = "reviewDate"
            )


            documentReference.collection("clientReviews").add(data).addOnSuccessListener {

                db.collection("users").document(uId!!).collection("requestedAppointment")
                    .document(id).update("appointment", "Reviewed").addOnSuccessListener {

                        Toast.makeText(requireContext(), "data is inserted successfully", Toast.LENGTH_SHORT).show()

                }
                    .addOnFailureListener {

                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()

                    }

                updateAndSetAverageRating(techId, clientRating.toDouble())

            }
                .addOnFailureListener {

                    Toast.makeText(requireContext(), "failed to insert data", Toast.LENGTH_SHORT)
                        .show()

                }

        }

        return binding.root
    }

    fun updateAndSetAverageRating(technicianId: String, newRating: Double) {
        val db = FirebaseFirestore.getInstance()

        // Reference to the technician's document
        val technicianDocRef = db.collection("workers").document(technicianId)

        // Fetch the current "rating" field value from the document
        technicianDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get the current rating
                    val currentRating = documentSnapshot.getDouble("rating") ?: 0.0

                    // Calculate the new average rating
                    val numRatings = 1
                    val totalRating = (currentRating * numRatings) + newRating
                    val averageRating: Double = totalRating / (numRatings + 1)


                    technicianDocRef.update("rating", averageRating)
                        .addOnSuccessListener {
                            // Update successful
                        }
                        .addOnFailureListener { e ->
                            // Handle errors
                        }
                }
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }

}