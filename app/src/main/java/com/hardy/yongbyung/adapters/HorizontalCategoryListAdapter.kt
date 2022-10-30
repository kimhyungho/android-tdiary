package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemHorizontalCategoryBinding
import com.hardy.yongbyung.model.CategoryUiModel

class HorizontalCategoryListAdapter :
    ListAdapter<CategoryUiModel, HorizontalCategoryListAdapter.ViewHolder>(
        FeedImageDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHorizontalCategoryBinding.inflate(
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
        private val binding: ItemHorizontalCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }
}

private class FeedImageDiffCallback : DiffUtil.ItemCallback<CategoryUiModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryUiModel,
        newItem: CategoryUiModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CategoryUiModel,
        newItem: CategoryUiModel
    ): Boolean {
        return oldItem == newItem
    }
}