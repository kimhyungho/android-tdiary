package com.hardy.yongbyung.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.hardy.yongbyung.databinding.ItemPlaceBinding
//import com.hardy.yongbyung.model.PlaceUiModel

//class PlaceListAdapter :
//    ListAdapter<PlaceUiModel, PlaceListAdapter.ViewHolder>(
//        PlaceDiffCallback()
//    ) {
//
//    var listener: Listener? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            ItemPlaceBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val place = getItem(position)
//        holder.bind(place)
//        holder.binding.locationCheckBox.setOnClickListener {
//            listener?.onPlaceCheck(place)
//        }
//    }
//
//    class ViewHolder(
//        val binding: ItemPlaceBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: PlaceUiModel) {
//            binding.apply {
//                uiModel = item
//                executePendingBindings()
//            }
//        }
//    }
//
//    interface Listener {
//        fun onPlaceCheck(item: PlaceUiModel)
//    }
//}
//
//private class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceUiModel>() {
//    override fun areItemsTheSame(
//        oldItem: PlaceUiModel,
//        newItem: PlaceUiModel
//    ): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(
//        oldItem: PlaceUiModel,
//        newItem: PlaceUiModel
//    ): Boolean {
//        return oldItem == newItem
//    }
//}