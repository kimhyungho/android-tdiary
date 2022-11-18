package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemPostBinding
import com.hardy.yongbyung.model.PostUiModel

class PostListAdapter : PagingDataAdapter<PostUiModel, PostListAdapter.ViewHolder>(
    PostDiffCallback()
) {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(post)
            holder.binding.itemLayout.setOnClickListener {
                listener?.onItemClick(post.id)
            }
            holder.binding.reportButton.setOnClickListener {
                listener?.onSirenClick(post.id)
            }
        }
    }

    class ViewHolder(
        val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostUiModel) {
            binding.apply {
                uiModel = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onItemClick(id: String)

        fun onSirenClick(id: String)
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostUiModel>() {
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