package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class DataDto(
	@SerializedName("after") val after: String?,
	@SerializedName("before") val before: String?,
	@SerializedName("dist") val dist: Int,
	@SerializedName("modhash") val modhash: String,
	@SerializedName("children") val posts: List<PostDto>
)