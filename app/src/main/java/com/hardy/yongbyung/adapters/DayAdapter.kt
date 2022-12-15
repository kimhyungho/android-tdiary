package com.hardy.yongbyung.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.domain.model.Post
import com.hardy.yongbyung.databinding.ItemDayBinding
import java.util.*

class DayAdapter(
    private val tempMonth: Int,
    private val dayList: MutableList<Date>
) : ListAdapter<Post, DayAdapter.ViewHolder>(
    DayDiffCallback()
) {
    val ROW = 6

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            tempMonth, dayList
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dayList[position]
//        val place = getItem(position)
        holder.bind(null)
        holder.itemView.setOnClickListener {
            listener?.onDayClick(null, date)
        }
    }

    class ViewHolder(
        val binding: ItemDayBinding,
        private val tempMonth: Int,
        private val dayList: MutableList<Date>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post?) {
            binding.apply {
                itemDayText.text = dayList[bindingAdapterPosition].date.toString()
                if (tempMonth != dayList[bindingAdapterPosition].month) {
                    itemDayText.alpha = 0.15f
                }
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }

    interface Listener {
        fun onDayClick(item: Post?, date: Date)
    }
}

private class DayDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem
    }
}