package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("images") val images: List<ImagesDto>,
    @SerializedName("enabled") val enabled: Boolean
)