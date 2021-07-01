package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class SecureMediaDto(
	@SerializedName("reddit_video") val redditVideo: RedditVideoDto
)