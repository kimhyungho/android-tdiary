package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemVerticalCategoryBinding
import com.hardy.yongbyung.model.CategoryUiModel

class VerticalCategoryListAdapter :
    ListAdapter<CategoryUiModel, VerticalCategoryListAdapter.ViewHolder>(
        VerticalCategoryDiffCallback()
    ) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVerticalCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val category = getItem(position)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(category)
        }
    }

    class ViewHolder(
        private val binding: ItemVerticalCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(category: CategoryUiModel)
    }
}

private class VerticalCategoryDiffCallback : DiffUtil.ItemCallback<CategoryUiModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryUiModel,
        newItem: CategoryUiModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: CategoryUiModel,
        newItem: CategoryUiModel
    ): Boolean {
        return oldItem == newItem
    }
}