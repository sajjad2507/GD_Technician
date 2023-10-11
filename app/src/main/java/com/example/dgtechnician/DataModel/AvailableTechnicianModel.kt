package com.example.dgtechnician.DataModel

class AvailableTechnicianModel(
    val techId: String? = "",
    val name: String? = "",
    val imageUri: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val appointmentType: String = "",
    val status: String? = "",
    val category: String? = "",
    val description: String? = "",
    val subCategory: String = "",
    val rating: Double? = 0.0
)