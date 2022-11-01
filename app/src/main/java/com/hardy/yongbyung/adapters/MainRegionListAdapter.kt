package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemMainRegionBinding
import com.hardy.yongbyung.model.MainRegionUiModel

class MainRegionListAdapter :
    ListAdapter<MainRegionUiModel, MainRegionListAdapter.ViewHolder>(
        MainRegionDiffCallback()
    ) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainRegionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val mainRegion = getItem(position)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(mainRegion.name)
        }
    }

    class ViewHolder(
        private val binding: ItemMainRegionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainRegionUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(name: String)
    }
}

private class MainRegionDiffCallback : DiffUtil.ItemCallback<MainRegionUiModel>() {
    override fun areItemsTheSame(
        oldItem: MainRegionUiModel,
        newItem: MainRegionUiModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: MainRegionUiModel,
        newItem: MainRegionUiModel
    ): Boolean {
        return oldItem == newItem
    }
}