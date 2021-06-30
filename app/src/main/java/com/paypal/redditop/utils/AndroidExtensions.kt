package com.paypal.redditop.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun ImageView.load(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(25))
        .into(this)
}