package com.example.dgtechnician.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dgtechnician.DataModel.AvailableTechnicianModel
import com.example.dgtechnician.R

class AvailableTechAdapter(
    val requireContext: Context,
    val bestTechList: ArrayList<AvailableTechnicianModel>,
    val catName: String,
    val requireParentFragment: Fragment
) :
    RecyclerView.Adapter<AvailableTechAdapter.BestTechHolder>() {

    inner class BestTechHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val techName = itemView.findViewById<TextView>(R.id.techName)
        val experience = itemView.findViewById<TextView>(R.id.techExpNo)
        val hiredRate = itemView.findViewById<TextView>(R.id.hiringRateNo)
        val techImage = itemView.findViewById<ImageView>(R.id.techImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestTechHolder {
        return BestTechHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_available_tech, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestTechHolder, position: Int) {

        holder.techName.text = bestTechList[position].name
        holder.experience.text = bestTechList[position].phoneNo
        holder.hiredRate.text = bestTechList[position].rating.toString()

        Glide.with(requireContext).load(bestTechList[position].imageUri).into(holder.techImage)

        holder.itemView.setOnClickListener {

            val catId = bestTechList[position].techId
            val bundle = Bundle()
            bundle.putString("myStringArg", catId)

            NavHostFragment.findNavController(requireParentFragment)
                .navigate(R.id.action_availableTechnician_to_techProfile, bundle)


        }

    }

    override fun getItemCount(): Int {
        return bestTechList.size
    }
}