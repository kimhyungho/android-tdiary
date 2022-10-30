package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.domain.model.Sport
import com.hardy.yongbyung.databinding.ItemSportBinding

class SportListAdapter :
    ListAdapter<Sport, SportListAdapter.ViewHolder>(
        FeedImageDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSportBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemSportBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sport) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }
}

private class FeedImageDiffCallback : DiffUtil.ItemCallback<Sport>() {
    override fun areItemsTheSame(
        oldItem: Sport,
        newItem: Sport
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Sport,
        newItem: Sport
    ): Boolean {
        return oldItem == newItem
    }
}