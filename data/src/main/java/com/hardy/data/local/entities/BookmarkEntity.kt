package com.hardy.data.local.entities

import androidx.room.Entity

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    val id: String,
    val category: String,
    val title: String,
    val content: String,
    val mainRegion: String,
    val subRegion: String,
    val location: String,
    val date: String,
    val createdAt: String
)