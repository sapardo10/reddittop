package com.paypal.redditop.data.network.dtos

import com.google.gson.annotations.SerializedName

data class RedditVideoDto(
	@SerializedName("bitrate_kbps") val bitrateKbps: Int,
	@SerializedName("dash_url") val dashUrl: String,
	@SerializedName("duration") val duration: Int,
	@SerializedName("fallback_url") val fallbackUrl: String,
	@SerializedName("height") val height: Int,
	@SerializedName("hls_url") val hlsUrl: String,
	@SerializedName("is_gif") val isGif: Boolean,
	@SerializedName("scrubber_media_url") val scrubberMediaUrl: String,
	@SerializedName("transcoding_status") val transcodingStatus: String,
	@SerializedName("width") val width: Int
)