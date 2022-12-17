package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemRecentPostBinding
import com.hardy.yongbyung.model.PostUiModel

class RecentPostAdapter :
    ListAdapter<PostUiModel, RecentPostAdapter.ViewHolder>(
        RecentPostUiModelDiffCallback()
    ) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecentPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val PostUiModel = getItem(position)
        holder.bind(PostUiModel)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(PostUiModel)
        }
    }

    class ViewHolder(
        val binding: ItemRecentPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(item: PostUiModel)
    }
}

private class RecentPostUiModelDiffCallback : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(
        oldItem: PostUiModel,
        newItem: PostUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PostUiModel,
        newItem: PostUiModel
    ): Boolean {
        return oldItem == newItem
    }
}