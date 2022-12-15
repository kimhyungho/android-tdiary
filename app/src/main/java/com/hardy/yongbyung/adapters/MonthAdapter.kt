package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hardy.domain.model.Post
import com.hardy.yongbyung.databinding.ItemMonthBinding
import java.util.*

class MonthAdapter : ListAdapter<Post, MonthAdapter.ViewHolder>(
    MonthDiffCallback()
) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMonthBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(null)
    }

    class ViewHolder(
        val binding: ItemMonthBinding,
        var listener: Listener?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post?) {
            binding.apply {
                val calendar = Calendar.getInstance()
                val center = Int.MAX_VALUE / 2

                calendar.time = Date()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.add(Calendar.MONTH, bindingAdapterPosition - center)

                itemMonthText.text =
                    "${calendar.get(Calendar.YEAR)}.${calendar.get(Calendar.MONTH) + 1}"
                val tempMonth = calendar.get(Calendar.MONTH)
                var dayList: MutableList<Date> = MutableList(6 * 7) { Date() }

                for (i in 0..5) {
                    for (k in 0..6) {
                        calendar.add(
                            Calendar.DAY_OF_MONTH,
                            (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k
                        )
                        dayList[i * 7 + k] = calendar.time
                    }
                    calendar.add(Calendar.WEEK_OF_MONTH, 1)
                }

                val dayListManager = GridLayoutManager(binding.root.context, 7)
                val dayListAdapter = DayAdapter(tempMonth, dayList).apply {
                    listener = object : DayAdapter.Listener {
                        override fun onDayClick(item: Post?, date: Date) {
                            this@ViewHolder.listener?.onDayClick(item, date)
                        }
                    }
                }

                itemMonthDayList.apply {
                    layoutManager = dayListManager
                    adapter = dayListAdapter
                }

                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    interface Listener {
        fun onDayClick(post: Post?, date: Date)
    }
}

private class MonthDiffCallback : DiffUtil.ItemCallback<Post>() {
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