package com.paypal.redditop.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.paypal.redditop.core.RedditopConstants.validImageExtensions


fun ImageView.load(
    context: Context,
    url: String,
    thumbnailUrl: String? = null,
    cornerRadius: Int? = null,
    fitCenter: Boolean = false
) {
    var builder = Glide.with(context).load(url)
    thumbnailUrl?.let {
        builder.thumbnail(
            Glide.with(context)
                .load(thumbnailUrl)
        )
    }


    cornerRadius?.let {
        builder
            .transform(RoundedCorners(it))
    }

    builder
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun String.isValidImageUrl(): Boolean {
    return validImageExtensions.find { this.contains(it) } != null
}