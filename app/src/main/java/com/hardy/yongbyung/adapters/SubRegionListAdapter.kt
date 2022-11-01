package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemSubRegionBinding
import com.hardy.yongbyung.model.SubRegionUiModel

class SubRegionListAdapter :
    ListAdapter<SubRegionUiModel, SubRegionListAdapter.ViewHolder>(
        SubRegionDiffCallback()
    ) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSubRegionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val subRegion = getItem(position)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(subRegion.name)
        }
    }

    class ViewHolder(
        private val binding: ItemSubRegionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SubRegionUiModel) {
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

private class SubRegionDiffCallback : DiffUtil.ItemCallback<SubRegionUiModel>() {
    override fun areItemsTheSame(
        oldItem: SubRegionUiModel,
        newItem: SubRegionUiModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: SubRegionUiModel,
        newItem: SubRegionUiModel
    ): Boolean {
        return oldItem == newItem
    }
}