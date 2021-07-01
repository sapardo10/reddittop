package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class BaseResponseDto(
	@SerializedName("data") val data: DataDto,
	@SerializedName("kind") val kind: String
)