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
//        val place = getItem(position)
        holder.bind(null)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.binding.root.context, "${dayList[position]}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    class ViewHolder(
        val binding: ItemDayBinding,
        private val tempMonth: Int,
        private val dayList: MutableList<Date>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post?) {
            binding.apply {
                itemDayText.text = dayList[position].date.toString()
                itemDayText.setTextColor(
                    when (position % 7) {
                        0 -> Color.RED
                        6 -> Color.BLUE
                        else -> Color.BLACK
                    }
                )

                if (tempMonth != dayList[position].month) {
                    itemDayText.alpha = 0.4f
                }
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }

    interface Listener {
        fun onPlaceCheck(item: Post)
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