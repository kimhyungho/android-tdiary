package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemReceiveMessageBinding
import com.hardy.yongbyung.databinding.ItemSendMessageBinding
import com.hardy.yongbyung.model.MessageUiModel

class MessageListAdapter : ListAdapter<MessageUiModel, RecyclerView.ViewHolder>(
    MessageDiffCallback()
) {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MessageUiModel.SEND_MESSAGE -> SendMessageViewHolder(
                ItemSendMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ReceiveMessageViewHolder(
                ItemReceiveMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (getItemViewType(position)) {
            MessageUiModel.SEND_MESSAGE -> (holder as? SendMessageViewHolder)?.bind(message)
            MessageUiModel.RECEIVE_MESSAGE -> (holder as? ReceiveMessageViewHolder)?.bind(message)
        }
    }

    class SendMessageViewHolder(
        private val binding: ItemSendMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    class ReceiveMessageViewHolder(
        private val binding: ItemReceiveMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(name: String)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
}

private class MessageDiffCallback : DiffUtil.ItemCallback<MessageUiModel>() {
    override fun areItemsTheSame(
        oldItem: MessageUiModel,
        newItem: MessageUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MessageUiModel,
        newItem: MessageUiModel
    ): Boolean {
        return oldItem == newItem
    }
}