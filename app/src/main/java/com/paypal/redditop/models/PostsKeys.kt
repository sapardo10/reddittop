package com.paypal.redditop.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class PostsKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val after: String?,
    val before: String?
)