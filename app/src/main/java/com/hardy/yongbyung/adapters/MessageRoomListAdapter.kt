package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemMessageRoomBinding
import com.hardy.yongbyung.model.MessageRoomUiModel

class MessageRoomListAdapter :
    ListAdapter<MessageRoomUiModel, MessageRoomListAdapter.ViewHolder>(
        MessageRoomDiffCallback()
    ) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMessageRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageRoom = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener?.onItemClick(messageRoom.id)
        }
    }

    class ViewHolder(
        private val binding: ItemMessageRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageRoomUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(id: String)
    }
}

private class MessageRoomDiffCallback : DiffUtil.ItemCallback<MessageRoomUiModel>() {
    override fun areItemsTheSame(
        oldItem: MessageRoomUiModel,
        newItem: MessageRoomUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MessageRoomUiModel,
        newItem: MessageRoomUiModel
    ): Boolean {
        return oldItem == newItem
    }
}