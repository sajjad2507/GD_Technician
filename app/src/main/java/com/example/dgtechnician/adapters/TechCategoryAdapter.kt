package com.example.dgtechnician.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dgtechnician.DataModel.TechCategoryModel
import com.example.dgtechnician.R

class TechCategoryAdapter(
    val requireContext: Context,
    val techCategoryList: ArrayList<TechCategoryModel>,
    val requireParentFragment: Fragment
) : RecyclerView.Adapter<TechCategoryAdapter.TechCategoryHolder>() {

    inner class TechCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val techCatName = itemView.findViewById<TextView>(R.id.techCatName)
        val techCatLogo = itemView.findViewById<ImageView>(R.id.techCatLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechCategoryHolder {
        return TechCategoryHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_cat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TechCategoryHolder, position: Int) {

        holder.techCatName.text = techCategoryList[position].name

        Glide.with(requireContext).load(techCategoryList[position].logo).into(holder.techCatLogo)

        holder.itemView.setOnClickListener {

            val catName = techCategoryList[position].name
            val bundle = Bundle()
            bundle.putString("catName", catName)

            NavHostFragment.findNavController(requireParentFragment)
                .navigate(R.id.action_home2_to_availableTechnician, bundle)

        }
    }

    override fun getItemCount(): Int {
        return techCategoryList.size
    }
}