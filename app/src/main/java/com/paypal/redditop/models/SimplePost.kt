package com.paypal.redditop.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class SimplePost(
    @PrimaryKey
    val id: String,
    val thumbnail: String,
    val title: String,
)
