package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class ImagesDto(
	@SerializedName("id") val id: String,
	@SerializedName("resolutions") val resolutions: List<ResolutionsDto>,
	@SerializedName("source") val source: SourceDto
)