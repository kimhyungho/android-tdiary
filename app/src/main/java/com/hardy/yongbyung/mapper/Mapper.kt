package com.hardy.yongbyung.mapper

interface Mapper<D, V> {
    fun mapToView(from: D): V
}