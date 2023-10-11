package com.example.dgtechnician.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtechnician.DataModel.AppointmentCheckModel
import com.example.dgtechnician.R

class SlotAdapter(
    val requireContext: Context,
    val slotList: ArrayList<AppointmentCheckModel>,
    val requireParentFragment: Fragment
) :
    RecyclerView.Adapter<SlotAdapter.BestTechHolder>() {

    interface OnItemClickListener {
        fun onItemClick(slotModel: AppointmentCheckModel)

    }

    private var itemClickListener: OnItemClickListener? = null

    inner class BestTechHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemSlotTv = itemView.findViewById<TextView>(R.id.itemSlotTv)
        val slotCard = itemView.findViewById<CardView>(R.id.slotCard)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
//                slotCard.setCardBackgroundColor(1)
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(slotList[position])
                    if (slotList[position].status != "booked") {
                        slotCard.setCardBackgroundColor(2)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestTechHolder {
        return BestTechHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_slots, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestTechHolder, position: Int) {
//
        holder.itemSlotTv.text = slotList[position].slot

        if (slotList[position].status == "booked") {
            holder.slotCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext,
                    R.color.mGray
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return slotList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}