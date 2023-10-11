package com.example.dgtechnician.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtechnician.DataModel.ChatMessageModel
import com.example.dgtechnician.R
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val currentUserId: String,
    private val chatId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messageList: ArrayList<ChatMessageModel> = ArrayList()

    fun addMessage(chatMessage: ChatMessageModel) {
        messageList.add(chatMessage)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sender_message, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receiver_message, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = messageList[position]

        when (holder) {
            is SenderViewHolder -> holder.bind(chatMessage)
            is ReceiverViewHolder -> holder.bind(chatMessage)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = messageList[position]
        return if (chatMessage.senderId == currentUserId) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)

        fun bind(chatMessage: ChatMessageModel) {
            messageTextView.text = chatMessage.message
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val timestamp = sdf.format(Date(chatMessage.timestamp))
            timestampTextView.text = timestamp
        }
    }

    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)

        fun bind(chatMessage: ChatMessageModel) {
            messageTextView.text = chatMessage.message
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val timestamp = sdf.format(Date(chatMessage.timestamp))
            timestampTextView.text = timestamp
        }
    }

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2
    }
}


