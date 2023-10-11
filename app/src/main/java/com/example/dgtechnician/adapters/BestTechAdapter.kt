package com.example.dgtechnician.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dgtechnician.DataModel.BestTechModel
import com.example.dgtechnician.R

class BestTechAdapter(val requireContext: Context, val bestTechList: ArrayList<BestTechModel>) :
    RecyclerView.Adapter<BestTechAdapter.BestTechHolder>() {

    inner class BestTechHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bestTechCat = itemView.findViewById<TextView>(R.id.catTxt)!!
        val bestTechName = itemView.findViewById<TextView>(R.id.nameTxt)
        val description = itemView.findViewById<TextView>(R.id.tagTxt)
        val imageView = itemView.findViewById<ImageView>(R.id.workerImg)
//        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestTechHolder {
        return BestTechHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_best_tech, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestTechHolder, position: Int) {

        holder.bestTechCat.text = bestTechList[position].catName
        holder.bestTechName.text = bestTechList[position].techName
        holder.description.text = bestTechList[position].description

//        holder.ratingBar.rating = bestTechList[position].rating.toString().toFloat()

        Glide.with(requireContext).load(bestTechList[position].imgUrl).into(holder.imageView)

//        holder.itemView.setOnClickListener {
//
//            sharedViewModel = ViewModelProvider(requireActivity).get(SharedViewModel::class.java)
//            sharedViewModel.sharedText = catList[position].id
//
//
//            val catNam = catList[position].name
//            val uId = catList[position].id
//
//
//            val bundle = Bundle()
//            bundle.putString("catName", catNam)
//            bundle.putString("uid", uId)
//
//            val fragment = BlankFragment2()
//            fragment.arguments = bundle
//
//            val sharedPreference: SharedPreferences? =
//                requireContext?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//            var editor = sharedPreference?.edit()
//            editor?.putString("uid", uId)
//            editor?.commit()
//
//            val transaction = fragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment, fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
    }

    override fun getItemCount(): Int {
        return bestTechList.size
    }
}