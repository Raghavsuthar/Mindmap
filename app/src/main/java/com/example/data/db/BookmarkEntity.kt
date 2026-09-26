package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val entityId: String,
    val layerType: String, // "CIRCUITS", "TRANSMITTERS", "SYNDROMES", "DRUGS"
    val title: String,
    val subtitle: String,
    val codeBadge: String,
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
