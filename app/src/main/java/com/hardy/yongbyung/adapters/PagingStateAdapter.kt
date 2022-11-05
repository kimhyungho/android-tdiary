package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.LayoutLoadingStateBinding

class PagingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagingStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_loading_state,
                parent,
                false
            ),
            retry
        )
    }

    class ViewHolder(
        private val binding: LayoutLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            with(binding) {
                with(retryButton) {
                    setOnClickListener {
                        retry.invoke()
                    }

                    visibility = if (loadState !is LoadState.Loading) View.VISIBLE
                    else View.GONE
                }
            }
        }
    }
}