package com.hardy.yongbyung.databinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.bindRefreshing(isRefreshing: Boolean){
    setRefreshing(isRefreshing)
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.bindOnRefresh(doNext: () -> Unit) {
    this.setOnRefreshListener { doNext.invoke() }
}