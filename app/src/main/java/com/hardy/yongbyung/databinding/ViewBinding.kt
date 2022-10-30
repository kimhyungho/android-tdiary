package com.hardy.yongbyung.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.bindVisibleOrGone(isGone: Boolean?) {
    this.visibility = if (isGone == true) View.VISIBLE else View.GONE
}