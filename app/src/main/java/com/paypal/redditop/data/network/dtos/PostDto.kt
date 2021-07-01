package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class PostDto(
	@SerializedName("data") val content: PostContentDto,
	@SerializedName("kind") val kind: String
)