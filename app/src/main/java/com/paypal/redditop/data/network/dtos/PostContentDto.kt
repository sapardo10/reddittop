package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class PostContentDto(
    @SerializedName("author") val author: String,
    @SerializedName("created_utc") val createdUtc: Long,
    @SerializedName("id") val id: String,
    @SerializedName("url_overridden_by_dest") val imageUrl: String?,
    @SerializedName("is_created_from_ads_ui") val isAds: Boolean,
    @SerializedName("is_video") val isVideo: Boolean,
    @SerializedName("mod_reason_title") val modReasonTitle: String,
    @SerializedName("name") val name: String,
    @SerializedName("num_comments") val numComments: Int,
    @SerializedName("over_18") val over18: Boolean,
    @SerializedName("permalink") val postUrl: String,
    @SerializedName("secure_media") val secureMedia: SecureMediaDto?,
    @SerializedName("subreddit") val subreddit: String,
    @SerializedName("subreddit_name_prefixed") val subredditNamePrefixed: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
    @SerializedName("total_awards_received") val totalAwardsReceived: Int,
    @SerializedName("ups") val ups: Int,
    @SerializedName("url") val url: String
)