package com.paypal.redditop.features.main

import androidx.recyclerview.widget.DiffUtil
import com.paypal.redditop.models.SimplePost

class SimplePostDiffUtilCallback : DiffUtil.ItemCallback<SimplePost>() {

    override fun areItemsTheSame(oldItem: SimplePost, newItem: SimplePost): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SimplePost, newItem: SimplePost): Boolean {
        return oldItem.id == newItem.id
                && oldItem.thumbnail == newItem.thumbnail
                && oldItem.title == oldItem.title
    }
}