package com.paypal.redditop.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class SimplePost(
    @PrimaryKey
    val id: String,
    val image: String,
    val thumbnail: String,
    val title: String,
    val upVotes: Int,
    val downVotes: Int,
    val upVoteRatio: Double,
    val videoUrl: String,
    val author: String,
    val authorUrl: String,
    val category: String,
    val subreddit: String,
    val subredditUrl: String,
    val created: Long,
    val isOriginalContent: Boolean,
    val isNsfw: Boolean,
    val isVideo: Boolean,
    val isAds: Boolean,
    val numComments: Int,
    val totalAwards: Int,
    val viewCount: String,
    val url: String
)
