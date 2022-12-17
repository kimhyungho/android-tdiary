package com.hardy.yongbyung.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardy.yongbyung.databinding.ItemMonthBinding
import com.hardy.yongbyung.model.PostUiModel
import okhttp3.internal.notify
import java.util.*

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.ViewHolder>() {
    private var posts: List<PostUiModel> = listOf()

    var listener: Listener? = null

    val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()

    inner class ViewHolder(val binding: ItemMonthBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        holder.binding.itemMonthText.text = "$year. ${month + 1}월"
        val tempMonth = calendar.get(Calendar.MONTH)

        var dayList: MutableList<Date> = MutableList(6 * 7) { Date() }
        for (i in 0..5) {
            for (k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1 - calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        val dayListManager = GridLayoutManager(holder.binding.root.context, 7)
        val dayListAdapter = DayAdapter(tempMonth, dayList)
        val monthPosts = this.posts.filter {
            it.date!!.year + 1900 == year && it.date.month == month
        }
        holder.binding.itemMonthDayList.layoutManager = dayListManager
        holder.binding.itemMonthDayList.adapter = dayListAdapter.apply {
            listener = object : DayAdapter.Listener {
                override fun onDayClick(post: PostUiModel?, date: Date) {
                    this@MonthAdapter.listener?.onDayClick(post, date)
                }
            }
        }
        dayListAdapter.setPosts(monthPosts)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPosts(posts: List<PostUiModel>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    interface Listener {
        fun onDayClick(post: PostUiModel?, date: Date)
    }
}