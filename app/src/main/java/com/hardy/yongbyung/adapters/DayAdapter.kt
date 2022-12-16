package com.hardy.yongbyung.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import com.hardy.domain.model.Post
import com.hardy.yongbyung.databinding.ItemDayBinding

class DayAdapter(val tempMonth: Int, val dayList: MutableList<Date>) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private var posts: List<Post> = listOf()

    val ROW = 6

    var listener: Listener? = null

    inner class ViewHolder(val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dayList[position]
        val day = date.date
        val post = posts.firstOrNull { it.date?.date == day }
        holder.binding.itemDayLayout.setOnClickListener { listener?.onDayClick(post, date) }
        holder.binding.itemDayText.text = day.toString()
        if (post != null) {
            holder.binding.itemDayText.text = "z"
        }

        if (tempMonth != dayList[position].month) {
            holder.binding.itemDayText.alpha = 0.15f
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }

    interface Listener {
        fun onDayClick(post: Post?, date: Date)
    }
}