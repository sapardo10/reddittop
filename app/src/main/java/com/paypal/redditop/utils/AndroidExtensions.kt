package com.paypal.redditop.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(context: Context, url: String) {
    Glide.with(this).load(url).into(this)
}