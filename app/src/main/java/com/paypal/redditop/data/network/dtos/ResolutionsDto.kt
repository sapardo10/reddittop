package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class ResolutionsDto(
	@SerializedName("height") val height: Int,
	@SerializedName("url") val url: String,
	@SerializedName("width") val width: Int
)